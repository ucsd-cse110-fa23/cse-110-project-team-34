package BackEnd.server;

import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.util.*;

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
        String name = query.substring(query.indexOf("=") + 1);

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder
                .append("<html>")
                .append("<body>")
                .append("<marquee direction='right' width='500'>")
                .append("<h1>")
                .append("Hello ")
                .append(name)
                .append("</h1>")
                .append("</marquee>")
                .append("<br>")
                .append("<img src=\"https://pngfre.com/wp-content/uploads/20220521_111044-1024x1024.png\" alt=\"Dog\" width=\"500\">") // height=\"600\">")
                .append("</body>")
                .append("</html>");

        // encode HTML content
        response = htmlBuilder.toString();
        return response;

    }
}
