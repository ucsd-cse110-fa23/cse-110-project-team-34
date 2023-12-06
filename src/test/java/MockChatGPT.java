import java.io.IOException;
import java.net.URISyntaxException;

import BackEnd.api.ChatGPT;
import FrontEnd.Recipe;

public class MockChatGPT extends ChatGPT{
    private Recipe mockedRecipe;

    public void setMockedRecipe(Recipe mockedRecipe) {
        this.mockedRecipe = mockedRecipe;
    }

    @Override
    public String runChatGPT(String option) throws IOException, InterruptedException, URISyntaxException {
        return "ChatGPT initiated";
    }

    @Override
    public boolean verifyIngredients(String ingredients){
        System.out.println("verifying ingredientd");
        return false;
    }

    @Override
    public String createRecipeAsJSONString(String ingredients, String mealType) throws IOException, InterruptedException, URISyntaxException{
        return "Successfully created recipe as JSON string";
    }

    
}