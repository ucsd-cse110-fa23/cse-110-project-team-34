package FrontEnd;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.text.ParseException;

public class JSONSaver{
    public static void saveRecipeList(RecipeList list, String fileName){
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
                recipeJSON.put("date", recipe.getDateCreated());
                recipeListArr.add(recipeJSON);
            }
        }

        recipeList.put("recipeList", recipeListArr);

        try {

			FileWriter file = new FileWriter(fileName);
			file.write(recipeList.toJSONString());
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

    }

    public static void updateJSON(String oldName, Recipe newRecipe){
        
        try{

            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader("storage.json");

            JSONObject recipeListObj = (JSONObject)parser.parse(fileReader);

            JSONArray recipeListArr = (JSONArray)recipeListObj.get("recipeList");

            for(int i = 0; i < recipeListArr.size(); i++){
                JSONObject recipe = (JSONObject)recipeListArr.get(i);
                
                if(((String)recipe.get("recipeName")).equals(oldName)){
                    recipe.put("recipeName", newRecipe.getRecipeName());
                    recipe.put("ingredients", newRecipe.getIngredients());
                    recipe.put("directions", newRecipe.getDirections());
                    recipe.put("date", newRecipe.getDateCreated());
                }
            }

            FileWriter file = new FileWriter("storage.json");
			file.write(recipeListObj.toJSONString());
			file.flush();
			file.close();

        }catch(IOException e){
            e.printStackTrace();
        }catch(org.json.simple.parser.ParseException e){
            e.printStackTrace();
        }
    }

    public static void removeByName(String name){
        
        try{

            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader("storage.json");

            JSONObject recipeListObj = (JSONObject)parser.parse(fileReader);

            JSONArray recipeListArr = (JSONArray)recipeListObj.get("recipeList");

            int i = 0;
            for(;i < recipeListArr.size(); i++){
                JSONObject recipe = (JSONObject)recipeListArr.get(i);
                
                if(((String)recipe.get("recipeName")).equals(name)){
                    break;
                }
            }

            recipeListArr.remove(i);

            FileWriter file = new FileWriter("storage.json");
			file.write(recipeListObj.toJSONString());
			file.flush();
			file.close();

        }catch(IOException e){
            e.printStackTrace();
        }catch(org.json.simple.parser.ParseException e){
            e.printStackTrace();
        }
    }

}