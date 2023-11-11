package main.java.FrontEnd;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import javafx.scene.control.Label;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.io.*;


/**
 * Simple recipe display for recipe list
 */
class RecipeSimple extends HBox{

    private Recipe recipe;
    private Label recipeName;
    private Button viewButton;


    RecipeSimple(Recipe r){
        this.setPrefSize(Constants.WINDOW_WIDTH-100, 60);
        this.setMinHeight(HBox.USE_PREF_SIZE);
        this.setStyle(Constants.defaultRecipeColor);
        this.setSpacing(10);
        this.setPadding(new Insets(20));

        recipe = r;

        recipeName = new Label(r.getRecipeName());
        recipeName.setStyle(Constants.defaultTextStyle);
        recipeName.setAlignment(Pos.CENTER_LEFT);

        Region growableRegion = new Region();
        HBox.setHgrow(growableRegion, Priority.ALWAYS);

        viewButton = new Button("View");
        viewButton.setStyle(Constants.viewButtonStyle);
        viewButton.setMinWidth(Button.USE_PREF_SIZE);
        viewButton.setAlignment(Pos.CENTER_RIGHT);

        this.getChildren().addAll(recipeName, growableRegion, viewButton);
        this.setAlignment(Pos.CENTER);
        
        addListeners();
    }

    public Button getViewButton(){
        return viewButton;
    }

    public Recipe getRecipe(){
        return recipe;
    }
    
    public void addListeners() {
    	viewButton.setOnAction(e -> {
    	Stage primaryStage = new Stage();
    	ViewRecipePageFrame ViewRecipePage = new ViewRecipePageFrame(this.recipe, primaryStage);
    	primaryStage.setScene(new Scene(ViewRecipePage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
    	primaryStage.setResizable(false);
    	primaryStage.show();
    	});
    }
}

/**
 * Reads a .JSON file with the recipes saved from previous uses.
 * Then populates the RecipeList with the existing recipes.
 */
public class RecipeList extends VBox{
    
    RecipeList() {
        this.setSpacing(4); // sets spacing between recipes
        this.setPrefSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.setStyle(Constants.defaultBackgroundColor);

        /**
         * Reads a .JSON file with the recipes saved from previous uses.
         * Then populates the RecipeList with the existing recipes.
         */
        try {
        	JSONParser parser = new JSONParser();

            JSONObject jsonObject;

            FileReader reader = new FileReader("example.json");

            if (reader.ready()) { //checks if file is empty
            	jsonObject = (JSONObject) parser.parse(reader); //Read JSON file

            	JSONArray recipeList = (JSONArray) jsonObject.get("recipeList");

            	for (int i = 0; i < recipeList.size(); i++) {
            		JSONObject recipe = (JSONObject) recipeList.get(i);
            		
            		String recipeName = (String) recipe.get("recipeName");
                    String ingredients = (String) recipe.get("ingredients");
                    String directions = (String) recipe.get("directions");
                    
                    
                    this.getChildren().add(new RecipeSimple(new Recipe(recipeName, ingredients, directions)));
                    // create new recipe
            	}
            }
        	reader.close();
        	//parser.close();
        	
        } catch (FileNotFoundException e) {
        	System.out.println("exception in RecipeList: file not found");
        } catch (IOException e) {
        	System.out.println("exception in RecipeList: io exception");
        } catch (ParseException e) {
        	System.out.println("exception in RecipeList: parse exception");
        } catch (Exception e) {
        	System.out.println("exception in RecipeList");
        }
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
        	stage.close();
            }
        );

        newEditButton.setOnAction(e -> {

            }
        );

        newDeleteButton.setOnAction(e -> {

            }
        );
        
    }
}
