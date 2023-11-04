package FrontEnd;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.text.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

class FrontPageFooter extends HBox {

    private Button newRecipeButton;

    FrontPageFooter() {
        this.setPrefSize(Constants.WINDOW_WIDTH, 100);
        this.setStyle(Constants.boldBackgroundColor);
        this.setSpacing(15);

        // set a default style for buttons - background color, font size, italics
        

        newRecipeButton = new Button("New Recipe"); // text displayed on add button
        newRecipeButton.setStyle(Constants.defaultButtonStyle); // styling the button

        this.getChildren().addAll(newRecipeButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center

    }

    public Button getNewRecipeButton() {
        return newRecipeButton;
    }

}

class FrontPageHeader extends HBox {

    FrontPageHeader() {
        this.setPrefSize(Constants.WINDOW_WIDTH, 100); // Size of the header
        this.setStyle(Constants.boldBackgroundColor);

        Text titleText = new Text("PantryPal"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 80;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }
}

class FrontPageFrame extends BorderPane{

    /**
     * Declare Scene Elements Here
     */
    private FrontPageHeader header;
    private FrontPageFooter footer;
    private ScrollPane recipeListScrollPane;
    private RecipeList recipeList;


    /**
     * Declare Scene Buttons Here
     */
    Button newRecipeButton;


    FrontPageFrame()
    {
        /**
         * Initialize / Assign Elements Here
         */
        header = new FrontPageHeader();
        footer = new FrontPageFooter();
        recipeList = new RecipeList();

        recipeListScrollPane = new ScrollPane(recipeList);
        recipeListScrollPane.setFitToWidth(true);
        recipeListScrollPane.setFitToHeight(true);
        
        newRecipeButton = footer.getNewRecipeButton();

        /**
         * Set element positions here
         */
        this.setTop(header);
        this.setCenter(recipeListScrollPane);
        this.setBottom(footer);


        //Add button listeners
        addListeners();
    }

    public void addListeners()
    {

        // Add button functionality
        newRecipeButton.setOnAction(e -> {

            }
        );
        
    }
}

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        //FrontPage layout
        FrontPageFrame frontPage = new FrontPageFrame();

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