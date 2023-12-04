package BackEnd.server;

import com.sun.net.httpserver.*;

import BackEnd.database.RecipeListDatabase;

import java.io.*;
import java.net.*;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CreateRecipeHandler implements HttpHandler{

    public CreateRecipeHandler(){

    }

    public void handle(HttpExchange httpExchange) throws IOException {
    String response = "Request Received";
    String method = httpExchange.getRequestMethod();

    try {
        if (method.equals("POST")) {
          response = handlePost(httpExchange);
        }else {
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

    private String handlePost(HttpExchange httpExchange) throws IOException {
      String response = "null";
      URI uri = httpExchange.getRequestURI();
      //No query needed, just audio data

      InputStream inStream = httpExchange.getRequestBody();
      Scanner scanner = new Scanner(inStream);
      String postData = scanner.nextLine();
      String recipeListJSONString = postData;

      JSONParser p = new JSONParser();
      JSONObject recipeListJSONObj = null;

      try{
        recipeListJSONObj = recipeListJSONObj = (JSONObject) p.parse(recipeListJSONString);  
      }catch(org.json.simple.parser.ParseException e){
          e.printStackTrace();
      }
      
      scanner.close();

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
