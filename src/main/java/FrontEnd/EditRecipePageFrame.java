package FrontEnd;

import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

class EditRecipePageHeader extends HBox {
	
	private Label editTitle;
	
	EditRecipePageHeader(Recipe recipe) {
		this.setPrefSize(Constants.WINDOW_WIDTH, 100); // Size of the header
        this.setStyle(Constants.boldBackgroundColor);
        
        editTitle = new Label("Edit Recipe"); // Text of the Header
        editTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 80;");
        this.getChildren().add(editTitle);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
	}

}


class EditRecipe extends VBox {
    private Label recipeNameLabel;
    private Label ingredientsLabel;
    private Label directionsLabel;
    private TextField recipeName;
    private TextArea ingredients;
    private TextArea directions;

    EditRecipe(Recipe recipe) {
        recipeNameLabel = new Label("Recipe Name: ");
        recipeName = new TextField(recipe.getRecipeName());
        ingredientsLabel = new Label("Ingredients: ");
        ingredients = new TextArea(recipe.getIngredients());
        directionsLabel = new Label("Directions: ");
        directions = new TextArea(recipe.getDirections());
        this.getChildren().addAll(recipeNameLabel, recipeName, ingredientsLabel, ingredients, directionsLabel, directions);
        this.setAlignment(Pos.CENTER);
    }
    
    public String getIngredients() {
    	return ingredients.getText();
    }
    
    public String getDirections() {
    	return directions.getText();
    }

    public String getRecipeName() {
        return recipeName.getText();
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


public class EditRecipePageFrame extends BorderPane {
	private EditRecipePageHeader header;
    private EditRecipePageFooter footer;
    private EditRecipe details;
    private Recipe recipe;
    private String originalRecipeName;
    private ViewRecipePageFrame parent;
    private Stage newStage;
    private String newRecipeName;
    private String newIngredients;
    private String newDirections;
    
    Button newBackButton;
    Button newSaveButton;
    
    EditRecipePageFrame(Recipe recipe, Stage newStage, ViewRecipePageFrame parent) {
    	this.originalRecipeName = recipe.getRecipeName();
        this.recipe = recipe;
    	this.parent = parent;
    	this.newStage = newStage;
    	header = new EditRecipePageHeader(this.recipe);
    	footer = new EditRecipePageFooter();
    	details = new EditRecipe(this.recipe);
        
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
        	newStage.close();
            }
        );

        newSaveButton.setOnAction(e -> {
        	// saves changes and returns to detailed view
        	newRecipeName = details.getRecipeName();
            newIngredients = details.getIngredients();
            newDirections = details.getDirections();
            
        	recipe.setRecipeName(newRecipeName);
        	recipe.setIngredients(newIngredients);
        	recipe.setDirections(newDirections);
        	
        	//open recipe details page with updated info
        	/**ViewRecipePageFrame ViewRecipePage = new ViewRecipePageFrame(this.recipe, stage);
        	stage.setScene(new Scene(ViewRecipePage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        	stage.setResizable(false);
        	stage.show();*/
            parent.refresh();
        	
        	//update RecipeList/file:  with new recipe list
            JSONSaver.updateJSON(originalRecipeName, recipe);

        	
        	newStage.close();//close edit page
            }
        );  
    }
}