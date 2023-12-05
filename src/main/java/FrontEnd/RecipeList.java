package FrontEnd;

import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
    private Label mealType;

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

        mealType = new Label(r.getMealType());
        mealType.setStyle(Constants.defaultTagStyle);
        mealType.setAlignment(Pos.CENTER_LEFT);

        Region growableRegion = new Region();
        HBox.setHgrow(growableRegion, Priority.ALWAYS);

        viewButton = new Button("View");
        viewButton.setStyle(Constants.viewButtonStyle);
        viewButton.setMinWidth(Button.USE_PREF_SIZE);
        viewButton.setAlignment(Pos.CENTER_RIGHT);

        this.getChildren().addAll(recipeName, growableRegion, mealType, viewButton);
        this.setAlignment(Pos.CENTER);
        
        addListeners();
    }

    public Button getViewButton(){
        return viewButton;
    }

    public Recipe getRecipe(){
        return recipe;
    }

    public String getRecipeName() {
        return recipeName.toString();
    }

    public void setRecipeName(String recipeName) {
        this.recipeName.setText(recipeName);
    }
    
    public void addListeners() {
    	viewButton.setOnAction(e -> {
    	Stage stage = (Stage) viewButton.getScene().getWindow();
    	ViewRecipePageFrame ViewRecipePage = new ViewRecipePageFrame(this.recipe, stage);
    	stage.setScene(new Scene(ViewRecipePage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
    	stage.setResizable(false);
    	stage.show();
    	});
    }
}

/**
 * Reads a .JSON file with the recipes saved from previous uses.
 * Then populates the RecipeList UI page with the existing recipes.
 */
class RecipeListDisplay extends VBox{

    RecipeListData recipeListData;
    
    public RecipeListDisplay(String fileName) {
        this.setSpacing(4); // sets spacing between recipes
        this.setPrefSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.setStyle(Constants.defaultBackgroundColor);

        recipeListData = new RecipeListData(fileName);

        /**
         * Reads a .JSON file with the recipes saved from previous uses.
         * Then populates the RecipeList with the existing recipes.
         */
        try {
        	JSONParser parser = new JSONParser();

            JSONObject jsonObject;

            FileReader reader = new FileReader(fileName);

            if (reader.ready()) { //checks if file is empty
            	jsonObject = (JSONObject) parser.parse(reader); //Read JSON file

            	JSONArray recipeList = (JSONArray) jsonObject.get("recipeList");

            	for (int i = 0; i < recipeList.size(); i++) { 
            		JSONObject recipe = (JSONObject) recipeList.get(i);

            		String recipeName = (String) recipe.get("recipeName");
                    String ingredients = (String) recipe.get("ingredients");
                    String directions = (String) recipe.get("directions");
                    String dateCreated = (String) recipe.get("date");
                    String mealType = (String) recipe.get("mealType");
                    
                    this.getChildren().add(new RecipeSimple(new Recipe(recipeName, ingredients, directions, dateCreated, mealType)));
            	}
            }
        	reader.close();
        	
        } catch (FileNotFoundException e) {
            JSONSaver.saveRecipeList(this, fileName);
        	//System.out.println("exception in RecipeList: file not found");
        } catch (IOException e) {
        	System.out.println("exception in RecipeList: io exception");
        } catch (ParseException e) {
        	System.out.println("exception in RecipeList: parse exception");
        } catch (Exception e) {
        	System.out.println("exception in RecipeList");
        }
    }
    RecipeListDisplay(RecipeListDisplay list){
        for(int i = list.getChildren().size() - 1; i >= 0; i--){//Makes this the reverse of list
            if(list.getChildren().get(i) instanceof RecipeSimple){
                Recipe temp = ((RecipeSimple)list.getChildren().get(i)).getRecipe();
                getChildren().add(new RecipeSimple(temp));
            }
        }
    }

    public RecipeListData getRecipeListData() {
        return this.recipeListData;
    }

    public void setRecipeListData(RecipeListData newRecipeListData) {
        this.recipeListData = newRecipeListData;
    }
}