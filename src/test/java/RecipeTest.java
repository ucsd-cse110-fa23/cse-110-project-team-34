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
        recipe = new Recipe("Chicken", "1 Chicken", "1. Roast Chicken", "2023-12-02T05:09:21.989948", "Lunch");
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
        assertEquals("1. Roast Chicken", recipe.getDirections());
    }

    @Test
    void testGetDateCreated() {
        assertEquals("2023-12-02T05:09:21.989948", recipe.getDateCreated());
    }

    @Test
    void testGetMealType() {
        assertEquals("Lunch", recipe.getMealType());
    }

    @Test
    void testSetRecipeName(){
        recipe.setRecipeName("Cow");
        assertEquals("Cow", recipe.getRecipeName());
    }

    @Test
    void testSetIngredients(){
        recipe.setIngredients("1 Cow");
        assertEquals("1 Cow", recipe.getIngredients());
    }

    @Test
    void testSetDirections(){
        recipe.setDirections("1. Boil the Cow");
        assertEquals("1. Boil the Cow", recipe.getDirections());
    }

    @Test
    void testSetDateCreated(){
        recipe.setDateCreated("1900-05-02T04:08:12.000000");
        assertEquals("1900-05-02T04:08:12.000000", recipe.getDateCreated());
    }

    @Test
    void testSetMealType(){
        recipe.setMealType("Dinner");
        assertEquals("Dinner", recipe.getMealType());
    }

}