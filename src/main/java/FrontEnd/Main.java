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

            }
        );
        
    }
}




class ViewRecipePageHeader extends HBox {
	
	ViewRecipePageHeader(Recipe recipe) {
		this.setPrefSize(Constants.WINDOW_WIDTH, 100); // Size of the header
        this.setStyle(Constants.boldBackgroundColor);
        
        Text titleText = new Text(recipe.getRecipeName()); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 80;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
	}
}


class ViewRecipe extends VBox {
    private Label ingredientsLabel;
    private Label directionsLabel;

    ViewRecipe(Recipe recipe) {
        ingredientsLabel = new Label("Ingredients: " + recipe.getIngredients());
        directionsLabel = new Label("Directions: " + recipe.getDirections());
        this.getChildren().addAll(ingredientsLabel, directionsLabel);
        this.setAlignment(Pos.CENTER);
    }
}


class ViewRecipePageFooter extends HBox {
	private Button BackButton;
	private Button EditButton;
	private Button DeleteButton;
	
	ViewRecipePageFooter() {
		this.setPrefSize(Constants.WINDOW_WIDTH, 100);
        this.setStyle(Constants.boldBackgroundColor);
        this.setSpacing(15);
        
        BackButton = new Button("Back"); // text displayed on add button
        BackButton.setStyle(Constants.defaultButtonStyle); // styling the button

        EditButton = new Button("Edit"); // text displayed on add button
        EditButton.setStyle(Constants.defaultButtonStyle);
        
        DeleteButton = new Button("Delete"); // text displayed on add button
        DeleteButton.setStyle(Constants.defaultButtonStyle);

        this.getChildren().addAll(BackButton, EditButton, DeleteButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center
	}
	
	public Button getBackButton() {
        return BackButton;
    }
	
	public Button getEditButton() {
        return EditButton;
    }
	
	public Button getDeleteButton() {
        return DeleteButton;
    }
	
}


class ViewRecipePageFrame extends BorderPane {
	
	/**
     * Declare Scene Elements Here
     */
    private ViewRecipePageHeader header;
    private ViewRecipePageFooter footer;
    private ScrollPane scrollPane;
    private ViewRecipe details;
    private Recipe recipe;
    private Stage stage;
    
    /**
     * Declare Scene Buttons Here
     */
    Button newBackButton;
    Button newEditButton;
    Button newDeleteButton;
    
    ViewRecipePageFrame(Recipe recipe, Stage stage) {
    	
    	/**
         * Initialize / Assign Elements Here
         */
    	this.recipe = recipe;
    	this.stage = stage;
    	header = new ViewRecipePageHeader(this.recipe);
    	footer = new ViewRecipePageFooter();
    	details = new ViewRecipe(this.recipe);
    	
    	scrollPane = new ScrollPane(details);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        newBackButton = footer.getBackButton();
        newEditButton = footer.getEditButton();
        newDeleteButton = footer.getDeleteButton();
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(header);

        /**
         * Set element positions here
         */
        this.setTop(vBox);
        this.setCenter(details);
        this.setBottom(footer);


        //Add button listeners
        addListeners();
    }
    
    public void addListeners() {

        // Add button functionality
        newBackButton.setOnAction(e -> {
        	
        	// returns to recipe list page
        	FrontPageFrame frontPage = new FrontPageFrame();
            this.stage.setTitle("PantryPal");
            this.stage.getIcons().add(new Image(Constants.defaultIconPath));
            this.stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            this.stage.setResizable(false);
            this.stage.show();
            }
        );

        newEditButton.setOnAction(e -> {
        	//opens new window with textfields to edit recipe
        	Stage newStage = new Stage();
        	EditRecipePageFrame editRecipePage = new EditRecipePageFrame(this.recipe, newStage);
        	newStage.setScene(new Scene(editRecipePage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        	newStage.setResizable(false);
        	newStage.show();
            }
        );

        newDeleteButton.setOnAction(e -> {
        	
        	//deletes recipe and returns to recipe list page
            }
        );
        
    }
}




class EditRecipePageHeader extends HBox {
	
	EditRecipePageHeader(Recipe recipe) {
		this.setPrefSize(Constants.WINDOW_WIDTH, 100); // Size of the header
        this.setStyle(Constants.boldBackgroundColor);
        
        TextField titleText = new TextField(recipe.getRecipeName()); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 80;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
	}
}


class EditRecipe extends VBox {
    private Label ingredientsLabel;
    private Label directionsLabel;
    private TextField ingredients;
    private TextField directions;

    EditRecipe(Recipe recipe) {
        ingredientsLabel = new Label("Ingredients: ");
        ingredients = new TextField(recipe.getIngredients());
        directionsLabel = new Label("Directions: ");
        directions = new TextField(recipe.getDirections());
        this.getChildren().addAll(ingredientsLabel, ingredients, directionsLabel, directions);
        this.setAlignment(Pos.CENTER);
    }
}


class EditRecipePageFooter extends HBox {
	private Button BackButton;
	private Button SaveButton;
	
	EditRecipePageFooter() {
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


class EditRecipePageFrame extends BorderPane {
	private EditRecipePageHeader header;
    private EditRecipePageFooter footer;
    private ScrollPane scrollPane;
    private EditRecipe details;
    private Recipe recipe;
    private Stage stage;
    
    Button newBackButton;
    Button newSaveButton;
    
    EditRecipePageFrame(Recipe recipe, Stage stage) {
    	this.recipe = recipe;
    	this.stage = stage;
    	header = new EditRecipePageHeader(this.recipe);
    	footer = new EditRecipePageFooter();
    	details = new EditRecipe(this.recipe);
    	
    	scrollPane = new ScrollPane(details);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        newBackButton = footer.getBackButton();
        newSaveButton = footer.getSaveButton();
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(header);
        
        this.setTop(vBox);
        this.setCenter(details);
        this.setBottom(footer);
        
        addListeners();
    }
    
    public void addListeners() {

        // Add button functionality
        newBackButton.setOnAction(e -> {
        	// returns to recipe list page
        	stage.close();
            }
        );

        newSaveButton.setOnAction(e -> {
        	// saves changes and returns to detailed view
        	
        	stage.close();
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