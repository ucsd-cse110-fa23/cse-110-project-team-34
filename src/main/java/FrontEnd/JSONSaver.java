package FrontEnd;

import org.json.simple.*;
import java.io.*;

public class JSONSaver{
    public static void saveRecipeList(RecipeList list){
        JSONObject recipeList = new JSONObject();
        JSONArray recipeListArr = new JSONArray();
        for(int i = 0; i < list.getChildren().size(); i++){
            if(list.getChildren().get(i) instanceof RecipeSimple){
                JSONObject recipeJSON = new JSONObject();
                RecipeSimple rs = (RecipeSimple) list.getChildren().get(i);
                Recipe recipe = rs.getRecipe();
                recipeJSON.put("recipeName", recipe.getRecipeName());
                recipeJSON.put("ingredients", recipe.getIngredients());
                recipeJSON.put("directions", recipe.getDirections());
                recipeListArr.add(recipeJSON);
            }
        }

        recipeList.put("recipeList", recipeListArr);

        try {

			FileWriter file = new FileWriter("storage.json");
			file.write(recipeList.toJSONString());
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

    }
}