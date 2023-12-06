package BackEnd.server;

import com.sun.net.httpserver.*;

import BackEnd.database.RecipeListDatabase;
import BackEnd.database.UserDatabase;

import java.io.*;
import java.net.*;
import java.util.*;

public class LoginRequestHandler implements HttpHandler{

    public LoginRequestHandler(){

    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
            response = handleLogin(httpExchange);
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
    
    private String handleLogin(HttpExchange httpExchange) throws IOException {
        String response = "Invalid Login request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getQuery();

        if(query == null) {
            return response;
        }

        String userName = query.substring(query.indexOf("=") + 1);
        String password = userName.substring(userName.indexOf("=")+1);
        userName = userName.substring(0, userName.indexOf("&"));

        if(!UserDatabase.usernameExists(userName)){
            return "null";
        }

        String userID = UserDatabase.getIdByUsernamePassword(userName, password);

        if(userID == null){
            response = "null";
        }else{
            response = userID + "\n" + RecipeListDatabase.getRecipelistByIdAsJSON(userID).toString();
        }
        

        return response;
    }

}
