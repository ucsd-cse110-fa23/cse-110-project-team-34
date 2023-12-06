package BackEnd.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

public class DallE {
    //Set the URL of the API Endpoint
    private static final String API_ENDPOINT = "https://api.openai.com/v1/images/generations";
    private static final String API_KEY = "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"; //Jason's API key
    private static final String MODEL = "dall-e-2";

    public String runDallE(String gptPrompt) throws IOException, InterruptedException, URISyntaxException {
        // Set request parameters
        String prompt = "Without adding text, in a 400x275 pixel rectangle, create a professional marketing photograph of the dish featured through these directions: ";
        int n = 1;
        prompt += gptPrompt;
    
    
        // Create a request body which you will pass into request object
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("n", n);
        requestBody.put("size", "256x256");
    
    
        // Create the HTTP client
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
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    
    
        // Process the response
        String responseBody = response.body();
    
    
        JSONObject responseJson = new JSONObject(responseBody);

        // Process the response
        String generatedImageURL = responseJson.getJSONArray("data").getJSONObject(0).getString("url");
        
        // System.out.println("DALL-E Response:");
        // System.out.println(generatedImageURL);

        return generatedImageURL;

        // Download the Generated Image to Current Directory
        // try(
        //     InputStream in = new URI(generatedImageURL).toURL().openStream()
        // )
        // {
        //     Files.copy(in, Paths.get("image.jpg"));
        // }
    }
}

