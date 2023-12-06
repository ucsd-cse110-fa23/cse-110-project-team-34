package FrontEnd;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;


/**
 * Stores data for RecipeList
 */
public class RecipeListData {

    ArrayList<Recipe> list;

    public RecipeListData(String fileName) {

        try {
        	JSONParser parser = new JSONParser();

            JSONObject jsonObject;

            FileReader reader = new FileReader(fileName);

            if (reader.ready()) { //checks if file is empty
            	jsonObject = (JSONObject) parser.parse(reader); //Read JSON file

            	JSONArray recipeList = (JSONArray) jsonObject.get("recipeList");

                list = new ArrayList<Recipe>(recipeList.size());

            	for (int i = 0; i < recipeList.size(); i++) { 
            		JSONObject recipe = (JSONObject) recipeList.get(i);

            		String recipeName = (String) recipe.get("recipeName");
                    String ingredients = (String) recipe.get("ingredients");
                    String directions = (String) recipe.get("directions");
                    String dateCreated = (String) recipe.get("date");
                    String mealType = (String) recipe.get("mealType");
                    String image = (String) recipe.get("image");

                    Recipe toAdd = new Recipe(recipeName, ingredients, directions, dateCreated, mealType);
                    toAdd.setImg(image);

                    list.add(toAdd);
            	}
            }
        	reader.close();
        	
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

    public ArrayList<Recipe> getRecipeList() {
        return this.list;
    }

    public void setRecipeList(ArrayList<Recipe> newList) {
        this.list = newList;
    }

    /**
     * Sorts RecipeList Alphabetically. Appears backward in data class, but
     * displays correctly because of how we read/write JSON files.
     */
    public void sortAlphabetically() {
        for (int i = 0; i < list.size(); i++) {

            for (int j = i + 1; j < list.size(); j++) {

                Recipe f = list.get(i);
                Recipe s = list.get(j);

                if (f.getRecipeName().compareToIgnoreCase(s.getRecipeName()) < 0) {
                    Collections.swap(list, i, j);
                }
            }
        }
    }
    
    /**
     * Sorts RecipeList Reverse Alphabetically. Appears backward in data class, but
     * displays correctly because of how we read/write JSON files.
     */
    public void sortReverseAlphabetically() {
        for (int i = 0; i < list.size(); i++) {

            for (int j = i + 1; j < list.size(); j++) {

                Recipe f = list.get(i);
                Recipe s = list.get(j);

                if (f.getRecipeName().compareToIgnoreCase(s.getRecipeName()) > 0) {
                    Collections.swap(list, i, j);
                }
            }
        }
    }

    /**
     * Sorts RecipeList from Newest to Oldest. Appears backward in data class, but
     * displays correctly because of how we read/write JSON files.
     */
    public void sortNewest() {
        for (int i = 0; i < list.size(); i++) {

            for (int j = i + 1; j < list.size(); j++) {

                Recipe f = list.get(i);
                Recipe s = list.get(j);

                if (LocalDateTime.parse(f.getDateCreated()).compareTo(LocalDateTime.parse(s.getDateCreated())) > 0) {
                    Collections.swap(list, i, j);
                }
            }
        }
    }

    /**
     * Sorts RecipeList from Oldest to Newest. Appears backward in data class, but
     * displays correctly because of how we read/write JSON files.
     */
    public void sortOldest() {
        for (int i = 0; i < list.size(); i++) {

            for (int j = i + 1; j < list.size(); j++) {

                Recipe f = list.get(i);
                Recipe s = list.get(j);

                if (LocalDateTime.parse(f.getDateCreated()).compareTo(LocalDateTime.parse(s.getDateCreated())) < 0) {

                    Collections.swap(list, i, j);
                }
            }
        }
    }

    /**
     * Filters RecipeList by Meal Type Tags.
     */
    public void filter(String type) {
        if (type != "All") {
            for (int i = list.size() - 1; i >= 0; i--) {
                Recipe f = list.get(i);

                if (f.getMealType().compareTo(type) != 0) {
                    list.remove(i);
                }
            }
        }
    }
}
