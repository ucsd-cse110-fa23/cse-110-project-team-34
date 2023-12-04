package FrontEnd;

import org.json.simple.parser.*;
import org.json.simple.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.net.URI;


public class HTTPRequestModel {

    /**
     * Performs login to server. Returns userID. Saves recipeList to local.
     * @param userName
     * @param password
     * @return userID if valid, null otherwise
     */
    public String performLoginRequest(String userName, String password){

        try {

            if(userName == null || password == null){return null;}

            String urlString = "http://localhost:8100/login" + "?" + "userName=" + userName + "&" + "password=" + password;

            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String userID = in.readLine();
            String recipeListJSONString = in.readLine();
            in.close();

            if(userID.trim().equals("null")){
                return null;
            }else{
                JSONSaver.saveRecipeListByJSON(JSONSaver.jsonStringToObject(recipeListJSONString));
                return userID;
            }
            
        } catch (Exception ex) {
            ErrorSys.quickErrorPopup("Error with the following request:\n" + "Method: Login");
            return null;
        }

    }

    /**
     * Performs signup request on the server
     * @param userName
     * @param password
     * @return 
     */
    public String performSignupRequest(String userName, String password) {

        try {

            if(userName == null || password == null){return null;}

            String urlString = "http://localhost:8100/signup" + "?" + "userName=" + userName + "&" + "password=" + password;

            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();

            if(response.trim().equals("null")){
                return null;
            }else{
                return response;
            }
            
        } catch (Exception ex) {
            ErrorSys.quickErrorPopup("Error with the following request:\n" + "Method: Signup");
            return null;
        }

    }

    public String performRecipeListGETRequest() {

        try {
            String urlString = "http://localhost:8100/recipelist?userID=" + UserID.userID;
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String recipeListJSONString = in.readLine();
            in.close();

            JSONSaver.saveRecipeListByJSON(JSONSaver.jsonStringToObject(recipeListJSONString));

            return "Success";
        } catch (Exception ex) {
            ErrorSys.quickErrorPopup("Error with the following request:\n" + "Method: GET recipelist");
            return "Error: " + ex.getMessage();
        }
    }

    public String performRecipeListPOSTRequest() {

        try {
            String urlString = "http://localhost:8100/recipelist?userID=" + UserID.userID;
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(Files.readString(Paths.get("storage.json")));
            out.flush();
            out.close();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();

            return response;
        } catch (Exception ex) {
            ErrorSys.quickErrorPopup("Error with the following request:\n" + "Method: POST recipelist");
            return "Error: " + ex.getMessage();
        }

    }

    public String performExampleRequest(String method, String query) {
        // Implement your HTTP request logic here and return the response

        try {
            String urlString = "http://localhost:8100/";
            if (query != null) {
                urlString += "?=" + query;
            }
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setDoOutput(true);

            if (method.equals("POST") || method.equals("PUT")) {
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write("");
                out.flush();
                out.close();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();
            return response;
        } catch (Exception ex) {
            ErrorSys.quickErrorPopup("Error with the following request:\n" + "Method: " + method + "\nQuery: " + query);
            return "Error: " + ex.getMessage();
        }
    }

}