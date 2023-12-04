package FrontEnd;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Set the title of the app
        primaryStage.setTitle("PantryPal");
        // Set the window icon
        primaryStage.getIcons().add(new Image(Constants.defaultIconPath));

        File userTxt = new File("user.txt");

        if(userTxt.exists()){
            try{
                String userID = Files.readString(Paths.get(userTxt.toURI()));
                if(userID != null && !userID.trim().equals("")){
                    UserID.setUserID(userID);
                
                    HTTPRequestModel httpRequestModel = new HTTPRequestModel();
                    String success = httpRequestModel.performRecipeListGETRequest();

                    System.out.println("userID " + userID);

                    if(success != null){
                        RecipeListPageFrame recipeListPF = new RecipeListPageFrame();

                        // Create scene of mentioned size with the border pane
                        primaryStage.setScene(new Scene(recipeListPF, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));

                        // Make window non-resizable
                        primaryStage.setResizable(false);
                        // Show the app
                        primaryStage.show();
                    }


                }else{

                    ErrorSys.quickErrorPopup("Something went wrong with automatic login!");
                    userTxt.delete();
                    start(primaryStage);

                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }else{

            //FrontPage layout
            LoginPageFrame frontPage = new LoginPageFrame();

            // Create scene of mentioned size with the border pane
            primaryStage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));

            // Make window non-resizable
            primaryStage.setResizable(false);
            // Show the app
            primaryStage.show();

        }
        
    }

    public static void main(String[] args) {
        launch(args);
    }

}