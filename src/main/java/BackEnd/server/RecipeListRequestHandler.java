package BackEnd.server;

import com.sun.net.httpserver.*;

import BackEnd.database.RecipeListDatabase;

import java.io.*;
import java.net.*;
import java.util.*;

import org.json.simple.JSONObject;

public class RecipeListRequestHandler implements HttpHandler{

    public RecipeListRequestHandler(){

    }

    public void handle(HttpExchange httpExchange) throws IOException {
    String response = "Request Received";
    String method = httpExchange.getRequestMethod();

    try {
        if (method.equals("GET")) {
          response = handleGet(httpExchange);
        } 
        else if (method.equals("POST")) {
          response = handlePost(httpExchange);
        } 
        else if (method.equals("PUT")) {
          response = handlePut(httpExchange);
        } 
        else if (method.equals("DELETE")) {
          response = handleDelete(httpExchange);
        }
        else {
          throw new Exception("Not Valid Request Method");
        }
      } catch (Exception e) {
        System.out.println("An erroneous request");
        response = e.toString();
        e.printStackTrace();
      }

      //Sending back response to the client
      httpExchange.sendResponseHeaders(200, response.length());
      OutputStream outStream = httpExchange.getResponseBody();
      outStream.write(response.getBytes());
      outStream.close();
    }
    
    //Get url should be in form https://localhost:8100/recipelist?userID=<USER_ID_HERE>
    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "null";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getQuery();
        if(query == null) {
            return response;
        }
        String userID = query.substring(query.indexOf("=") + 1);

        JSONObject recipeList = RecipeListDatabase.getRecipelistByIdAsJSON(userID);

        if(recipeList == null){
          return response;
        }

        return recipeList.toString();
    }

    private String handlePost(HttpExchange httpExchange) throws IOException {
        String response = "";
        return response;
    }

    private String handlePut(HttpExchange httpExchange) throws IOException {
        String response = "";
        return response;
    }

    private String handleDelete(HttpExchange httpExchange) throws IOException {
        String response = "";
        return response;
    }
}
