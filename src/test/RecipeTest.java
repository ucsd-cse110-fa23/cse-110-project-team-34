package test;
import FrontEnd.Recipe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecipeTest {
    
    private Recipe recipe;
    
    @BeforeEach
    void setUp() {
        recipe = new Recipe("Chicken", "1 Chicken", "1.Roast Chicken at 350F for 20 minutes");
    }
    
    @Test
    void testGetRecipeName() {
        assertEquals("Chicken", recipe.getRecipeName());
    }

    @Test
    void testGetIngredients() {
        assertEquals("1 Chicken", recipe.getIngredients());
    }

    @Test
    void testGetDirections() {
        assertEquals("1.Roast Chicken at 350F for 20 minutes", recipe.getDirections());
    }
    
}