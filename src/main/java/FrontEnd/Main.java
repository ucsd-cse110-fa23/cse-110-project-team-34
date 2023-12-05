package FrontEnd;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.*;


public class Main extends Application {

    public static Controller controller;    
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
                        controller = new RecipeListPageFrameController(recipeListPF, httpRequestModel);
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
            // Create scene of mentioned size with the border pane
            LoginPageFrame frontPage = new LoginPageFrame();
            HTTPRequestModel model = new HTTPRequestModel();
            controller = new LoginPageFrameController(frontPage, model);
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

    // // create interface controller, use controller type.
    public static void setController(Controller newcontroller) {
        controller = newcontroller;
    }

}