package FrontEnd;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

class RecipeListPageHeader extends HBox {

    RecipeListPageHeader() {
        this.setPrefSize(Constants.WINDOW_WIDTH, 100); // Size of the header
        this.setStyle(Constants.boldBackgroundColor);

        Text titleText = new Text("PantryPal"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 80;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }
}

class RecipeListPageFooter extends HBox {

    private Button newRecipeButton;

    RecipeListPageFooter() {
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

public class RecipeListPageFrame extends BorderPane{

    /**
     * Declare Scene Elements Here
     */
    private RecipeListPageHeader header;
    private RecipeListPageFooter footer;
    private VBox recipeListComplete;
    private ScrollPane recipeListScrollPane;
    private Label recipeListLabel;
    private RecipeList recipeList;
    private RecipeList reversedList;

    /**
     * Declare Scene Buttons Here
     */
    Button newRecipeButton;

    // getter
    public Button getNewRecipeButton() {
        return newRecipeButton;
    }
    public RecipeList getRecipeList() {
        return recipeList;
    }
    public RecipeList getReversedList() {
        return reversedList;
    }

    RecipeListPageFrame()
    {
        /**
         * Initialize / Assign Elements Here
         */
        header = new RecipeListPageHeader();
        footer = new RecipeListPageFooter();
        recipeList = new RecipeList(); //default constructor reads .json file
        recipeListComplete = new VBox();

        recipeListLabel = new Label("Recipe List:");
        recipeListLabel.setStyle(Constants.defaultTextStyle);
        recipeListLabel.setPadding(new Insets(10));
        reversedList = new RecipeList(recipeList); //Uses new RecipeList constructor to reverse the order

        recipeListScrollPane = new ScrollPane(reversedList);
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
        // Add button functionality (just changes the Stage to the NewRecipePageFrame)
        newRecipeButton.setOnAction(e -> {
            // Stage primaryStage = new Stage();
            // //need to pass in recipeList so recipes can be added to it
            // //need to pass in reversedList so recipes can be added to it
            // NewRecipePageFrame NewRecipePage = new NewRecipePageFrame(recipeList, reversedList); 
            // primaryStage.setScene(new Scene(NewRecipePage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            // primaryStage.setResizable(false);
            // primaryStage.show();
            Stage stage = (Stage) newRecipeButton.getScene().getWindow();
            NewRecipePageFrame NewRecipePage = new NewRecipePageFrame(stage, recipeList, reversedList);
            stage.setScene(new Scene(NewRecipePage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();

        });
    }


    public void setNewRecipeButtonAction(EventHandler<ActionEvent> eventHandler) {
        newRecipeButton.setOnAction(eventHandler);
    }
}