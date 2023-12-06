import BackEnd.api.ChatGPT;
import BackEnd.api.Whisper;
import FrontEnd.Recipe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChatGPTTest {
    private ChatGPT chatGPT = new MockChatGPT();
    private Whisper whisper;
    private String prompt;
    private String ingred;
    private String name;
    private String meal;
    
    @BeforeEach
    void setUp() {
        prompt = "Prepare me a recipe for lunch; I have Chicken as my ingredient";
        ingred = "1 Chicken";
        name = "Chicken";
        meal = "Lunch";
    }

    @Test
    void runGPT() {
        String expected = "ChatGPT initiated for prompt: Prepare me a recipe for lunch; I have Chicken as my ingredient" ;
        try{
            assertEquals(expected, chatGPT.runChatGPT(prompt));
        }catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    void VerifyBadIngredients() {
        assertEquals(false, chatGPT.verifyIngredients("bad ingredients"));
    }

    @Test
    void VerifyGoodIngredients() {
        assertEquals(true, chatGPT.verifyIngredients("good ingredients"));
    }

    @Test
    void GenerateRecipeName() {
        String expected = "New recipe name for ingredients: 1 Chicken and meal type: Lunch";
        assertEquals(expected, chatGPT.createRecipeName(ingred,meal));
    }

    @Test
    void GenerateIngredientList() {
        String expected = "Create new ingredient list for ingredients: 1 Chicken with recipe name: Chicken";
        assertEquals(expected, chatGPT.createIngredientList(ingred,name));
    }

    @Test
    void GenerateRecipeDirections() {
        String expected = "Create recipe directions for ingredients: 1 Chicken with recipe name: Chicken";
        assertEquals(expected, chatGPT.createRecipeDirections(name,ingred));
    }
}