import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import FrontEnd.ErrorSys;
import FrontEnd.HTTPRequestModel;
import FrontEnd.MultiPartFormDataHelper;

public class CreateRecipeRequestTest {
    public static void main(String[] args){
        HTTPRequestModel httpRequestModel = new HTTPRequestModel();
        String audioText = httpRequestModel.performCreateRecipeRequest();


        System.out.println(audioText);
    }
}
