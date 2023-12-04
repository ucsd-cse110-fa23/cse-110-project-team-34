package BackEnd.server;

import com.sun.net.httpserver.*;

import BackEnd.database.RecipeListDatabase;

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
        String query = uri.getRawQuery();
        String userID = query.substring(query.indexOf("=") + 1);
        String recipeName = userID.substring(userID.indexOf("=")+1);
        userID = userID.substring(0,userID.indexOf("recipeName"));
        //userID now contains userID and recipeName now contains recipeName

        JSONObject recipe = RecipeListDatabase.getRecipeByIdAndNameAsJSON(userID, recipeName);

        String ingredients = (String) recipe.get("ingredients");
        String directions = (String) recipe.get("directions");


        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder
                .append("<html>")
                .append("<body>")
                .append("<h1>")
                .append("Hello ")
                .append("</h1>")
                .append("<br>")
                .append("<img src=\"recipeImage.jpg\" alt=\"" + recipeName + " image\" width=\"500\">") // height=\"600\">")
                .append("<br>")
                .append("<h2>")
                .append("Ingredients: " + ingredients)
                .append("</h2>")
                .append("<br>")
                .append("<h2>")
                .append("Directions: " + directions)
                .append("</h2>")
                .append("</body>")
                .append("</html>");

        // encode HTML content
        response = htmlBuilder.toString();
        return response;

    }
}
