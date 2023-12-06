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
    private String dateCreated;
    private String mealType;
    private String img; //DallE

    public Recipe(String name, String ingred, String direc, String date, String meal){
        recipeName = name;
        ingredients = ingred;
        directions = direc;
        dateCreated = date;
        mealType = meal;
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

    public String getDateCreated() {
        return dateCreated;
    }

    public String getMealType() {
        return mealType;
    }

    public String getImg() {
        return img;
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

    public void setDateCreated(String date) {
        dateCreated = date;
    }

    public void setMealType(String meal) {
        mealType = meal;
    }

    public void setImg(String i) {
        img = i;
    }

}


