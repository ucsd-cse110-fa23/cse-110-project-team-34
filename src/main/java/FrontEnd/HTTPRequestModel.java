package FrontEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
            
        } catch (ConnectException  ex) {
            ErrorSys.quickErrorPopup("Error: Server Unavailable");
            return null;
        }catch(Exception e){
            e.printStackTrace();
            ErrorSys.quickErrorPopup("Unknown Error!");
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
            
        } catch (ConnectException  ex) {
            ErrorSys.quickErrorPopup("Error: Server Unavailable");
            return null;
        }catch(Exception e){
            ErrorSys.quickErrorPopup("Unknown Error!");
            return null;
        }

    }

    /**
     * Performs create recipe and returns the new recipe as a JSON String
     * @return Returns the recipe JSON String
     */
    public String performCreateRecipeRequest(String mealType){

        try {
            long longBound = System.currentTimeMillis();
            String urlString = "http://localhost:8100/createRecipe?bound="+longBound+"&mealtype="+mealType;
            URL url = new URI(urlString).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //Send CSE110Voice.wav
            File file = new File(Constants.defaultAudioPath);
            
            // Set up request headers
            String boundary = "Boundary-" + longBound;
            conn.setRequestProperty(
                "Content-Type",
                "multipart/form-data; boundary=" + boundary
            );

            //Create output stream
            OutputStream out = conn.getOutputStream();

            //Write model parameter
            MultiPartFormDataHelper.writeParameterToOutputStream(out, "model", "whisper-1", boundary);

            // Write file to request body
            MultiPartFormDataHelper.writeFileToOutputStream(out, file, boundary);


            //Write closing boundary to request body
            out.write(("\r\n--" + boundary + "--\r\n").getBytes());
            
            //Cleanup output stream
            out.flush();
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String recipeJSONString = in.readLine();
            in.close();

            return recipeJSONString;
        } catch (ConnectException  ex) {
            ErrorSys.quickErrorPopup("Error: Server Unavailable");
            return null;
        }catch(Exception e){
            ErrorSys.quickErrorPopup("Unknown Error!");
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
        } catch (ConnectException  ex) {
            ErrorSys.quickErrorPopup("Error: Server Unavailable");
            return null;
        }catch(Exception e){
            ErrorSys.quickErrorPopup("Unknown Error!");
            return null;
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
            out.write(Files.readString(Paths.get("storage.json"), StandardCharsets.UTF_8));
            out.flush();
            out.close();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String response = in.readLine();
            in.close();

            if(response.equals("Success!")){
                
            }

            return "SUCCESS_POST_REQUEST";
        } catch (ConnectException  ex) {
            ErrorSys.quickErrorPopup("Error: Server Unavailable");
            return null;
        }catch(Exception e){
            ErrorSys.quickErrorPopup("Unknown Error! POST");
            e.printStackTrace();
            return null;
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
        } catch (ConnectException  ex) {
            ErrorSys.quickErrorPopup("Error: Server Unavailable");
            return null;
        }catch(Exception e){
            ErrorSys.quickErrorPopup("Unknown Error!");
            return null;
        }
    }

}