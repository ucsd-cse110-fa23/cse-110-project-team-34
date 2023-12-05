package BackEnd.server;

import com.sun.net.httpserver.*;

import BackEnd.database.RecipeListDatabase;
import FrontEnd.Constants;

import java.io.*;
import java.net.*;
import java.util.*;

import org.json.simple.JSONObject;

/*
     * A share URL should have the format https://localhost:8100/share?userID=<User ID Goes Here>&recipeName=<RecipeNameGoesHere> 
*/
public class ShareRequestHandler implements HttpHandler{

    public ShareRequestHandler(){

    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else {
                throw new Exception("Not Valid Request Method");
            }
        } catch (Exception e) {
            System.out.println("An erroneous request");
            response = e.toString();
            e.printStackTrace();
        }

        // Sending back response to the client
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream outStream = httpExchange.getResponseBody();
        outStream.write(response.getBytes());
        outStream.close();

    }

    private String handleGet(HttpExchange httpExchange) throws IOException {
        String response = "Invalid GET request";
        URI uri = httpExchange.getRequestURI();
        String query = uri.getQuery();
        String userID = query.substring(query.indexOf("=") + 1);
        String recipeName = userID.substring(userID.indexOf("=")+1);
        userID = userID.substring(0,userID.indexOf("&"));
        //userID now contains userID and recipeName now contains recipeName

        //System.out.println("userID " + userID);
        //System.out.println("recipeName " + recipeName);

        JSONObject recipe = RecipeListDatabase.getRecipeByIdAndNameAsJSON(userID, recipeName);

        String ingredients = (String) recipe.get("ingredients");
        String directions = (String) recipe.get("directions");


        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder
                .append("<!DOCTYPE html>")
                .append("<html>")

                //Head
                .append("<head>")
                .append("<title>PantryPal - " + recipeName + "</title>")
                .append("</head>")

                //Body
                .append("<body style=\"background-color: #C6F5E4;\">")
                .append("<header style=\"background-color: #3EB489; margin: 0; padding: 0;\"><h1 style=\"font-family:arial;margin:0;text-align:center;\">PantyPal</h1></header>")

                .append("<h2 style=\"font-family:arial;text-align:left;\">")
                .append("Recipe Name: " + recipeName)
                .append("</h2>")
                .append("<br>")
                .append("<img style=\"display:block;margin-left:auto;margin-right:auto;\" src=\"recipeImage.jpg\" alt=\"" + recipeName + " image\" width=\"500\">") // height=\"600\">")
                .append("<br>")
                .append("<h2 style=\"font-family:arial;text-align:left;\">")
                .append("Ingredients: " + ingredients)
                .append("</h2>")
                .append("<br>")
                .append("<h2 style=\"font-family:arial;text-align:left;\">")
                .append("Directions: " + directions)
                .append("</h2>")
                .append("</body>")
                .append("</html>");

        // encode HTML content
        response = htmlBuilder.toString();
        return response;

    }
}
