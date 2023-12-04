package FrontEnd;

import org.json.simple.parser.*;
import org.json.simple.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public String performAppRequest(String method, String query) {
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