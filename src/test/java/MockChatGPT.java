import java.io.IOException;
import java.net.URISyntaxException;
import BackEnd.api.ChatGPT;
import FrontEnd.Recipe;



public class MockChatGPT extends ChatGPT{
    /*private Recipe mockedRecipe;

    public void setMockedRecipe(Recipe mockedRecipe) {
        this.mockedRecipe = mockedRecipe;
    }*/

    @Override
    public String runChatGPT(String option) throws IOException, InterruptedException, URISyntaxException{
        return "ChatGPT initiated for prompt: " + option;
    }

    @Override
    public boolean verifyIngredients(String ingredients){
        if(ingredients.equals("bad ingredients")){
            return false;
        }
        return true;
    }

    @Override
    public String createRecipeName(String ingredients, String mealType){
        return "New recipe name for ingredients: "+ingredients+" and meal type: "+mealType;
    }

    @Override
    public String createIngredientList(String ing, String recipeName){
        return "Create new ingredient list for ingredients: "+ing+" with recipe name: "+recipeName;
    }

    @Override
    public String createRecipeDirections(String recipeName, String ingredientList){
        return "Create recipe directions for ingredients: "+ingredientList+" with recipe name: "+recipeName;
    }
}