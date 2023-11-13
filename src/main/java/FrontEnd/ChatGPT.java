package FrontEnd;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;

// import org.json.*;
// import org.json.simple.*;
import org.json.JSONArray;
import org.json.JSONObject;
// import org.json.simple.JSONArray;
// import org.json.simple.JSONObject;
// import org.json.simple.parser.*;

public class ChatGPT {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "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";
    // could change maybe
    private static final String MODEL = "text-davinci-003";

    public String[] runChatGPT(String option) throws IOException, InterruptedException, URISyntaxException{

        // Set request parameters
        // change prompt to the json file, which we can get from reading mp3 file with whisper.
        // option for breakfast, lunch or dinner
        // 
        String prompt = option;
        int maxTokens = 120;

        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", 1.0);

        // Create the HTTP Client
        HttpClient client = HttpClient.newHttpClient();

        // Create the request object
        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(API_ENDPOINT))
        .header("Content-Type", "application/json")
        .header("Authorization", String.format("Bearer %s", API_KEY))
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build();

        // Send the request and receive the response
        HttpResponse<String> response = client.send(
        request,
        HttpResponse.BodyHandlers.ofString()
        );
 
        // Process the res  ponse
        String responseBody = response.body();

        

        JSONObject responseJson = new JSONObject(responseBody);


        JSONArray choices = responseJson.getJSONArray("choices");
        String generatedText = choices.getJSONObject(0).getString("text");
        System.out.println("generated text: " + generatedText);
        //String output = generatedText;
        String[] output = parseOutput(generatedText);
        return output;
    }

    private String[] parseOutput(String chatOutput) {
            // can use lastIndexOf(string)
            String lines[] = chatOutput.split("#");
            return lines;
    }
}