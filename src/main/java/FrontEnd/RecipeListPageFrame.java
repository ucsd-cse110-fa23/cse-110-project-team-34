package FrontEnd;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
    private HBox buttonMenu;
    private AnchorPane left;
    private AnchorPane right;

    /**
     * Declare Scene Buttons Here
     */
    Button newRecipeButton;

    // Declare Elements for Dropdown
    MenuButton sortButton;
    MenuItem alphaOpt;
    MenuItem reverseOpt;
    MenuItem newOpt;
    MenuItem oldOpt;

    RecipeListPageFrame(String sortMenu, String fileName)
    {
        /**
         * Initialize / Assign Elements Here
         */
        header = new RecipeListPageHeader();
        footer = new RecipeListPageFooter();
        recipeList = new RecipeList(fileName); //default constructor reads .json file
        recipeListComplete = new VBox();
        buttonMenu = new HBox();
        right = new AnchorPane();
        left = new AnchorPane();

        // Initialize DropDown
        sortButton = new MenuButton(sortMenu);
        alphaOpt = new MenuItem("A-Z");
        reverseOpt = new MenuItem("Z-A");
        newOpt = new MenuItem("Newest");
        oldOpt = new MenuItem("Oldest");
        sortButton.getItems().addAll(alphaOpt, reverseOpt, newOpt, oldOpt);
        sortButton.setStyle(Constants.defaultButtonStyle);
        sortButton.setPrefHeight(1);

        recipeListLabel = new Label("Recipe List:");
        recipeListLabel.setStyle(Constants.defaultTextStyle);
        recipeListLabel.setPadding(new Insets(10));
        reversedList = new RecipeList(recipeList); //Uses new RecipeList constructor to reverse the order

        // Align Menu Bar Elements
        left.getChildren().add(recipeListLabel);
        HBox.setHgrow(left, Priority.ALWAYS);
        right.getChildren().add(sortButton);

        recipeListScrollPane = new ScrollPane(reversedList);
        recipeListScrollPane.setFitToWidth(true);
        recipeListScrollPane.setFitToHeight(true);

        // Populate Menu
        buttonMenu.setPrefSize(Constants.WINDOW_WIDTH, 10);
        buttonMenu.getChildren().addAll(left,right);

        recipeListComplete.setStyle(Constants.defaultBackgroundColor);
        recipeListComplete.getChildren().addAll(buttonMenu, recipeListScrollPane);
        
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

    public MenuButton getSortButton() {
        return sortButton;
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

        alphaOpt.setOnAction(e -> {

            recipeList.sortAlphabetically();

            // Reload Sorted Recipe List from alternate json file
            JSONSaver.saveRecipeList(recipeList, "app.json");
            Stage stage = (Stage) newRecipeButton.getScene().getWindow();
            RecipeListPageFrame frontPage = new RecipeListPageFrame(alphaOpt.getText(), "app.json");
            stage.setTitle("PantryPal");
            stage.getIcons().add(new Image(Constants.defaultIconPath));
            stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();
        });

        reverseOpt.setOnAction(e -> {
 
            recipeList.sortReverseAlphabetically();

            // Reload Sorted Recipe List from alternate json file
            JSONSaver.saveRecipeList(recipeList, "app.json");
            Stage stage = (Stage) newRecipeButton.getScene().getWindow();
            RecipeListPageFrame frontPage = new RecipeListPageFrame(reverseOpt.getText(), "app.json");
            stage.setTitle("PantryPal");
            stage.getIcons().add(new Image(Constants.defaultIconPath));
            stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();
        });

        newOpt.setOnAction(e -> {

            recipeList.sortNewest();

            // Reload Sorted Recipe List from alternate json file
            JSONSaver.saveRecipeList(recipeList, "app.json");
            Stage stage = (Stage) newRecipeButton.getScene().getWindow();
            RecipeListPageFrame frontPage = new RecipeListPageFrame(newOpt.getText(), "app.json");
            stage.setTitle("PantryPal");
            stage.getIcons().add(new Image(Constants.defaultIconPath));
            stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();
        });

        oldOpt.setOnAction(e -> {

            recipeList.sortOldest();

            // Reload Sorted Recipe List from alternate json file
            JSONSaver.saveRecipeList(recipeList, "app.json");
            Stage stage = (Stage) newRecipeButton.getScene().getWindow();
            RecipeListPageFrame frontPage = new RecipeListPageFrame(oldOpt.getText(), "app.json");
            stage.setTitle("PantryPal");
            stage.getIcons().add(new Image(Constants.defaultIconPath));
            stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();
        });
    }
}