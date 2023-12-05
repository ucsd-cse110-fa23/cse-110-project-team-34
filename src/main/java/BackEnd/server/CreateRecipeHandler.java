package BackEnd.server;

import com.sun.net.httpserver.*;

import BackEnd.api.ChatGPT;
import BackEnd.api.Whisper;
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
      String query = uri.getQuery();
      String boundaryNum = query.substring(query.indexOf("=")+1);
      String mealType = boundaryNum.substring(boundaryNum.indexOf("=")+1);
      boundaryNum = boundaryNum.substring(0, boundaryNum.indexOf("&"));

      InputStream inStream = httpExchange.getRequestBody();
      
      Whisper whisper = new Whisper();
      String audioText = "";

      try{
        audioText = whisper.readAudio(inStream, boundaryNum);
      }catch(URISyntaxException urie){
        urie.printStackTrace();
      }catch(IOException ioe){
        ioe.printStackTrace();
      }

      if(audioText == null || audioText.trim().equals("")){
        return "EMPTY_RECORDING_ERROR";
      }

      ChatGPT chatGPT = new ChatGPT();


      int count = 0;
      int maxTries = 3;
      while(true) {
        try {
            response = chatGPT.createRecipeAsJSONString(audioText, mealType);
            if(response == null){
              throw new Exception("ChatGPT Response Invalid");
            }
            break;
        }catch (Exception e) {
            if (++count >= maxTries){
              return "CHAT_GPT_FAILED_ERROR";
            };
        }
      }

      return response;
    }

}
