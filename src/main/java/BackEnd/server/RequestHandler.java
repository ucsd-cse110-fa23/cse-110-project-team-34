package BackEnd.server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class RequestHandler implements HttpHandler{

    public RequestHandler(){

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
    
    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getRawQuery();
        if(query == null) {
            return response;
        }
        if(query.equals("getRecipelistByIdAsJSON")) {

        }
        else if(query.equals("getUsernameById")) {

        }

        return response;
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
