package FrontEnd;
/**
 * This Recipe class is not for display, but rather just for data.
 * The display classes in RecipeList.java and _________ take in a Recipe Object
 * in their constructor.
 */
public class Recipe{ 
    
    private String recipeName;
    private String ingredients;
    private String directions;

    public Recipe(String name, String ingred, String direc){
        recipeName = name;
        ingredients = ingred;
        directions = direc;
    }

    //Getters
    public String getRecipeName(){
        return recipeName;
    }

    public String getIngredients(){
        return ingredients;
    }

    public String getDirections(){
        return directions;
    }

    //Setters
    public void setRecipeName(String n){
        recipeName = n;
    }

    public void setIngredients(String i){
        ingredients = i;
    }

    public void setDirections(String d){
        directions = d;
    }

}


