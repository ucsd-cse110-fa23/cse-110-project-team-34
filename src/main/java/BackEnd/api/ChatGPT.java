package BackEnd.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

// import org.json.*;
// import org.json.simple.*;
import org.json.JSONArray;
import org.json.JSONObject;
// import org.json.simple.JSONArray;
// import org.json.simple.JSONObject;
// import org.json.simple.parser.*;
import org.json.simple.parser.JSONParser;

public class ChatGPT {
    private static final String API_ENDPOINT = "https://api.openai.com/v1/completions";
    private static final String API_KEY = "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";
    // could change maybe
    private static final String MODEL = "text-davinci-003";

    public String runChatGPT(String option) throws IOException, InterruptedException, URISyntaxException{

        // Set request parameters
        // change prompt to the json file, which we can get from reading mp3 file with whisper.
        // option for breakfast, lunch or dinner
        // 
        String prompt = option;
        int maxTokens = 250;

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
        //String[] output = parseOutput(generatedText);
        return generatedText;
    }

    public boolean verifyIngredients(String ingredients){
        String prompt = "Would you say the following items surrounded by brackets are generally ingredients? [" + ingredients + "]" + " If you believe so, please respond with 'YES'. If you don't believe so, please respond with 'NO'.";
        
        String valid = "no";
        try{
            valid = runChatGPT(prompt);
        }catch(Exception e){
            e.printStackTrace();
        }
            
        System.out.println(valid);

        if(valid.toLowerCase().trim().equals("yes")){
            return true;
        }else{
            return false;
        }
    }

    public String createRecipeAsJSONString(String ingredients, String mealType){
        /**String recipeName = null;
        String recipeIngredientList = null;
        String recipeDirections = null;

        if(true){//verifyIngredients(ingredients)){ Verifying ingredients list not needed right now. Prompt needs to be edited.
            recipeName = createRecipeName(ingredients, mealType);
        }else{
            return "INVALID_INGREDIENTS_ERROR";
        }

        if(recipeName != null){
            recipeIngredientList = createIngredientList(ingredients, recipeName);
            recipeDirections = createRecipeDirections(recipeName, recipeIngredientList);
        }

        JSONObject recipe = new JSONObject();
        recipe.put("recipeName", recipeName);
        recipe.put("ingredients", recipeIngredientList);
        recipe.put("directions", recipeDirections);

        return recipe.toString();**/

        String recipeNamePrompt = "Create a recipe that is for " + mealType + " that uses the following ingredients surrounded in brackets [" + ingredients + "]. Please respond with only the recipe name and nothing else.";
        String recipeIngredientListPrompt = "Create an ingredients list as a comma separated list that includes the ingredient and the quantity in parentheses and is for the dish indicated by the value associated with the key 'recipeName' that includes the following ingredients '" + ingredients + "' and any other ingredients needed. Please respond with only the ingredients list and nothing else.";
        String recipeDirectionsPrompt = "Create directions as a numbered list for the dish indicated by the value associated with the key 'recipeName' that uses the ingredient list indicated by the value associated with the key 'ingredients'. Please respond with only the directions and nothing else.";
        String prompt = "Respond in the form of a single line json object formatted string where the key \"recipeName\" is assigned to value equal to the response to the following prompt indicated by brackets [" + recipeNamePrompt + "], the key \"ingredients\" is assigned to value equal to the response to the following prompt indicated by brackets [" + recipeIngredientListPrompt + "], and the final key \"directions'\" is assigned to value equal to the response to the following prompt indicated by brackets [" + recipeDirectionsPrompt + "]. Make sure to use double quotes for the json object keys.";

        String recipeJSONString = null;

        try{
            recipeJSONString = runChatGPT(prompt);
            recipeJSONString = recipeJSONString.substring(recipeJSONString.indexOf("{"), recipeJSONString.indexOf("}")+1);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

        //System.out.println(recipeJSONString);

        JSONParser p = new JSONParser();
        org.json.simple.JSONObject jsonObj = null;
        try{
            jsonObj = (org.json.simple.JSONObject) p.parse(recipeJSONString);
            jsonObj.get("recipeName");
            jsonObj.get("ingredients");
            jsonObj.get("directions");
        }catch(org.json.simple.parser.ParseException e){
            e.printStackTrace();
            return null;
        }
        
        return jsonObj.toString();

    }

    public String createRecipeName(String ingredients, String mealType){
        String prompt = "Create a recipe that is for " + mealType + " that uses the following ingredients surrounded in brackets [" + ingredients + "]. Please respond with only the recipe name and nothing else.";
        
        String recipeName = null;
        try{
            recipeName = runChatGPT(prompt);
        }catch(Exception e){
            e.printStackTrace();
        }

        return recipeName.trim();
    }

    public String createIngredientList(String ing, String recipeName){
        String prompt = "Create an ingredients list as a comma separated list that includes the ingredient and the quantity in parentheses and is for the following dish '" + recipeName + "'' that includes the following ingredients '" + ing + "' and any other ingredients needed. Please respond with only the ingredients list and nothing else.";
        
        String ingredients = null;
        try{
            ingredients = runChatGPT(prompt);
        }catch(Exception e){
            e.printStackTrace();
        }

        return ingredients.trim();
    }

    public String createRecipeDirections(String recipeName, String ingredientList){
        String prompt = "Create directions as a numbered list for the following dish '" + recipeName + "'' that uses the following ingredients '" + ingredientList + "'. Please respond with only the directions and nothing else.";
        
        String directions = null;
        try{
            directions = runChatGPT(prompt);
        }catch(Exception e){
            e.printStackTrace();
        }

        return directions.trim();
    }

    private String[] parseOutput(String chatOutput) {
            // can use lastIndexOf(string)
            String lines[] = chatOutput.split("#");
            return lines;
    }


}