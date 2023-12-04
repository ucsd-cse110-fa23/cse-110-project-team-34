package BackEnd.server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class RootRequestHandler implements HttpHandler{

    public RootRequestHandler(){

    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "Request Received";
        String method = httpExchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
                response = handleGet(httpExchange);
            } else {
                response = handleInvalid(httpExchange);
                //throw new Exception("Not Valid Request Method");
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
        String response = "Response Received";

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder
                .append("<html>")
                .append("<body>")
                .append("<h1>")
                .append("Welcome to PantryPal")
                .append("</h1>")
                .append("<br>")
                .append("</body>")
                .append("</html>");

        // encode HTML content
        response = htmlBuilder.toString();
        return response;

    }

    private String handleInvalid(HttpExchange httpExchange) throws IOException {
        String response = "Invalid request";

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder
                .append("<html>")
                .append("<body>")
                .append("<h1>")
                .append("Invalid Request")
                .append("</h1>")
                .append("<br>")
                .append("</body>")
                .append("</html>");

        // encode HTML content
        response = htmlBuilder.toString();
        return response;

    }
}
