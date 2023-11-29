package FrontEnd;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        //FrontPage layout
        LoginPageFrame frontPage = new LoginPageFrame();
        
        // Set the title of the app
        primaryStage.setTitle("PantryPal");
        // Set the window icon
        primaryStage.getIcons().add(new Image(Constants.defaultIconPath));


        // Create scene of mentioned size with the border pane
        primaryStage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));

        // Make window non-resizable
        primaryStage.setResizable(false);
        // Show the app
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}