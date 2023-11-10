package main.java.FrontEnd;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

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

class NewRecipePageHeader extends HBox {
    private Button Breakfast;
    private Button Lunch;
    private Button Dinner;

    NewRecipePageHeader() {

        Breakfast = new Button("Breakfast");
        Lunch = new Button("Lunch");
        Dinner = new Button("Dinner");
        this.setSpacing(15);  

        Breakfast.setStyle(Constants.defaultButtonStyle);
        Lunch.setStyle(Constants.defaultButtonStyle);
        Dinner.setStyle(Constants.defaultButtonStyle);

        this.setPrefSize(Constants.WINDOW_WIDTH, 100); // Size of the header
        this.setStyle(Constants.boldBackgroundColor);


        this.getChildren().addAll(Breakfast, Lunch, Dinner);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }
}

class FrontPageFooter extends HBox {

    private Button newRecipeButton;

    FrontPageFooter() {
        this.setPrefSize(Constants.WINDOW_WIDTH, 100);
        this.setStyle(Constants.boldBackgroundColor);
        this.setSpacing(15);      

        newRecipeButton = new Button("New Recipe"); // text displayed on add button
        newRecipeButton.setStyle(Constants.defaultButtonStyle); // styling the button

        this.getChildren().addAll(newRecipeButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center

    }

    public Button getNewRecipeButton() {
        return newRecipeButton;
    }

}

class RecipeGenerator extends HBox {
    private Label voiceLabel;
    private Button generator;

    RecipeGenerator() {
        voiceLabel = new Label("Voice Command");
        voiceLabel.setStyle(Constants.defaultTextStyle);

        generator = new Button("Generate");
        generator.setStyle(Constants.defaultButtonStyle);

        this.getChildren().addAll(voiceLabel,generator);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(60);
    }
    public Button getGenerator() {
        return generator;
    }
}

class RecipeContent extends VBox {
    private Label recipeNameLabel;
    private Label ingredientsLabel;
    private Label directionsLabel;

    RecipeContent(Recipe recipe) {
        recipeNameLabel = new Label("Recipe Name: " + recipe.getRecipeName());
        ingredientsLabel = new Label("Ingredients: " + recipe.getIngredients());
        directionsLabel = new Label("Directions: " + recipe.getDirections());
        this.getChildren().addAll(recipeNameLabel, ingredientsLabel, directionsLabel);
        this.setAlignment(Pos.CENTER);
    }
}


class NewRecipePageFooter extends HBox {

    private Button BackButton;
    private Button SaveButton;


    NewRecipePageFooter() {
        this.setPrefSize(Constants.WINDOW_WIDTH, 100);
        this.setStyle(Constants.boldBackgroundColor);
        this.setSpacing(15);      

        BackButton = new Button("Back"); // text displayed on add button
        BackButton.setStyle(Constants.defaultButtonStyle); // styling the button

        SaveButton = new Button("Save"); // text displayed on add button
        SaveButton.setStyle(Constants.defaultButtonStyle);

        this.getChildren().addAll(BackButton, SaveButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center

    }

    public Button getBackButton() {
        return BackButton;
    }

    public Button getSaveButton() {
        return SaveButton;
    }

}


class FrontPageFrame extends BorderPane{

    /**
     * Declare Scene Elements Here
     */
    private FrontPageHeader header;
    private FrontPageFooter footer;
    private VBox recipeListComplete;
    private ScrollPane recipeListScrollPane;
    private Label recipeListLabel;
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
        recipeListComplete = new VBox();

        recipeListLabel = new Label("Recipe List:");
        recipeListLabel.setStyle(Constants.defaultTextStyle);
        recipeListLabel.setPadding(new Insets(10));

        recipeListScrollPane = new ScrollPane(recipeList);
        recipeListScrollPane.setFitToWidth(true);
        recipeListScrollPane.setFitToHeight(true);

        recipeListComplete.setStyle(Constants.defaultBackgroundColor);
        recipeListComplete.getChildren().addAll(recipeListLabel, recipeListScrollPane);
        
        newRecipeButton = footer.getNewRecipeButton();

        /**
         * Set element positions here
         */
        this.setTop(header);
        this.setCenter(recipeListComplete);
        this.setBottom(footer);


        //Add button listeners
        addListeners();
    }

    public void addListeners()
    {

        // Add button functionality
        newRecipeButton.setOnAction(e -> {
            Stage primaryStage = new Stage();
            NewRecipePageFrame NewRecipePage = new NewRecipePageFrame();
            primaryStage.setScene(new Scene(NewRecipePage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            primaryStage.setResizable(false);
            primaryStage.show();
        });
    
        
    }
}


class NewRecipePageFrame extends BorderPane{

    /**
     * Declare Scene Elements Here
     */
    private NewRecipePageHeader header;
    private NewRecipePageFooter footer;
    private ScrollPane scrollPane;
    private RecipeGenerator generator;
    private Recipe recipe;
    private RecipeContent content;

    /**
     * Declare Scene Buttons Here
     */
    Button newSaveButton;
    Button newBackButton;
    Button newGenerateButton;


    NewRecipePageFrame()
    {
        /**
         * Initialize / Assign Elements Here
         */

        recipe = new Recipe("Sample Recipe", "Sample Ingredients", "Sample Directions");
        header = new NewRecipePageHeader();
        footer = new NewRecipePageFooter();
        generator = new RecipeGenerator();
        content = new RecipeContent(recipe);
        

        scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        newBackButton = footer.getBackButton();
        newSaveButton = footer.getSaveButton();
        newGenerateButton = generator.getGenerator();

        VBox vBox = new VBox();
        vBox.getChildren().addAll(header, generator);

        /**
         * Set element positions here
         */
        this.setTop(vBox);
        this.setCenter(content);
        this.setBottom(footer);


        //Add button listeners
        addListeners();
    }

    public void addListeners()
    {

        // Add button functionality
        newBackButton.setOnAction(e -> {

            }
        );

        newSaveButton.setOnAction(e -> {

            }
        );

        newGenerateButton.setOnAction(e -> {

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