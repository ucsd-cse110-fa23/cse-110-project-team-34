import FrontEnd.Recipe;
import FrontEnd.RecipeListData;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecipeListTest {
    private RecipeListData recipeList;
    private ArrayList<Recipe> list;
    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipeList = new RecipeListData("example.json");
    }

    // Unit Tests for Sort & Filter

    @Test
    void testRecipeListConstructor() {

        list = recipeList.getRecipeList();
        assertEquals(3, list.size());

        recipe = list.get(0);
        assertEquals("PB+J Sandwich", recipe.getRecipeName());
        assertEquals("bread, peanut butter, jam", recipe.getIngredients());
        assertEquals("1. take the bread", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Breakfast", recipe.getMealType());

        recipe = list.get(1);
        assertEquals("Apple Pie", recipe.getRecipeName());
        assertEquals("crust, apple filling, etc.", recipe.getIngredients());
        assertEquals("... 5. Put in oven", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838296", recipe.getDateCreated());
        assertEquals("Lunch", recipe.getMealType());
        
        recipe = list.get(2);
        assertEquals("Lasagna", recipe.getRecipeName());
        assertEquals("pasta, meat, cheese", recipe.getIngredients());
        assertEquals("Step 1, Step 2, Step 3", recipe.getDirections());
        assertEquals("2023-12-01T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Dinner", recipe.getMealType());
    }

    @Test
    void testSortAlphabetically() {

        recipeList.sortAlphabetically();
        list = recipeList.getRecipeList();

        assertEquals(3, list.size());

        recipe = list.get(0);
        assertEquals("PB+J Sandwich", recipe.getRecipeName());
        assertEquals("bread, peanut butter, jam", recipe.getIngredients());
        assertEquals("1. take the bread", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Breakfast", recipe.getMealType());

        recipe = list.get(1);
        assertEquals("Lasagna", recipe.getRecipeName());
        assertEquals("pasta, meat, cheese", recipe.getIngredients());
        assertEquals("Step 1, Step 2, Step 3", recipe.getDirections());
        assertEquals("2023-12-01T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Dinner", recipe.getMealType());

        recipe = list.get(2);
        assertEquals("Apple Pie", recipe.getRecipeName());
        assertEquals("crust, apple filling, etc.", recipe.getIngredients());
        assertEquals("... 5. Put in oven", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838296", recipe.getDateCreated());
        assertEquals("Lunch", recipe.getMealType());
    }

    @Test
    void testSortReverseAlphabetically() {
        
        recipeList.sortReverseAlphabetically();
        list = recipeList.getRecipeList();

        assertEquals(3, list.size());

        recipe = list.get(0);
        assertEquals("Apple Pie", recipe.getRecipeName());
        assertEquals("crust, apple filling, etc.", recipe.getIngredients());
        assertEquals("... 5. Put in oven", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838296", recipe.getDateCreated());
        assertEquals("Lunch", recipe.getMealType());

        recipe = list.get(1);
        assertEquals("Lasagna", recipe.getRecipeName());
        assertEquals("pasta, meat, cheese", recipe.getIngredients());
        assertEquals("Step 1, Step 2, Step 3", recipe.getDirections());
        assertEquals("2023-12-01T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Dinner", recipe.getMealType());

        recipe = list.get(2);
        assertEquals("PB+J Sandwich", recipe.getRecipeName());
        assertEquals("bread, peanut butter, jam", recipe.getIngredients());
        assertEquals("1. take the bread", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Breakfast", recipe.getMealType());
    }

    @Test
    void testSortNewest() {

        recipeList.sortNewest();
        list = recipeList.getRecipeList();

        assertEquals(3, list.size());

        recipe = list.get(0);
        assertEquals("Apple Pie", recipe.getRecipeName());
        assertEquals("crust, apple filling, etc.", recipe.getIngredients());
        assertEquals("... 5. Put in oven", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838296", recipe.getDateCreated());
        assertEquals("Lunch", recipe.getMealType());

        recipe = list.get(1);
        assertEquals("PB+J Sandwich", recipe.getRecipeName());
        assertEquals("bread, peanut butter, jam", recipe.getIngredients());
        assertEquals("1. take the bread", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Breakfast", recipe.getMealType());

        recipe = list.get(2);
        assertEquals("Lasagna", recipe.getRecipeName());
        assertEquals("pasta, meat, cheese", recipe.getIngredients());
        assertEquals("Step 1, Step 2, Step 3", recipe.getDirections());
        assertEquals("2023-12-01T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Dinner", recipe.getMealType());
    }

    @Test
    void testSortOldest() {

        recipeList.sortOldest();
        list = recipeList.getRecipeList();

        assertEquals(3, list.size());

        recipe = list.get(0);
        assertEquals("Lasagna", recipe.getRecipeName());
        assertEquals("pasta, meat, cheese", recipe.getIngredients());
        assertEquals("Step 1, Step 2, Step 3", recipe.getDirections());
        assertEquals("2023-12-01T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Dinner", recipe.getMealType());

        recipe = list.get(1);
        assertEquals("PB+J Sandwich", recipe.getRecipeName());
        assertEquals("bread, peanut butter, jam", recipe.getIngredients());
        assertEquals("1. take the bread", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Breakfast", recipe.getMealType());

        recipe = list.get(2);
        assertEquals("Apple Pie", recipe.getRecipeName());
        assertEquals("crust, apple filling, etc.", recipe.getIngredients());
        assertEquals("... 5. Put in oven", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838296", recipe.getDateCreated());
        assertEquals("Lunch", recipe.getMealType());
    }

    @Test
    void testFilterAll() {

        recipeList.filter("All");
        list = recipeList.getRecipeList();

        assertEquals(3, list.size());

        recipe = list.get(0);
        assertEquals("PB+J Sandwich", recipe.getRecipeName());
        assertEquals("bread, peanut butter, jam", recipe.getIngredients());
        assertEquals("1. take the bread", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Breakfast", recipe.getMealType());

        recipe = list.get(1);
        assertEquals("Apple Pie", recipe.getRecipeName());
        assertEquals("crust, apple filling, etc.", recipe.getIngredients());
        assertEquals("... 5. Put in oven", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838296", recipe.getDateCreated());
        assertEquals("Lunch", recipe.getMealType());
        
        recipe = list.get(2);
        assertEquals("Lasagna", recipe.getRecipeName());
        assertEquals("pasta, meat, cheese", recipe.getIngredients());
        assertEquals("Step 1, Step 2, Step 3", recipe.getDirections());
        assertEquals("2023-12-01T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Dinner", recipe.getMealType());
    }

    @Test
    void testFilterBreakfast() {

        recipeList.filter("Breakfast");
        list = recipeList.getRecipeList();
        
        assertEquals(1, list.size());

        recipe = list.get(0);
        assertEquals("PB+J Sandwich", recipe.getRecipeName());
        assertEquals("bread, peanut butter, jam", recipe.getIngredients());
        assertEquals("1. take the bread", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Breakfast", recipe.getMealType());
    }

    @Test
    void testFilterLunch() {

        recipeList.filter("Lunch");
        list = recipeList.getRecipeList();
        
        assertEquals(1, list.size());

        recipe = list.get(0);
        assertEquals("Apple Pie", recipe.getRecipeName());
        assertEquals("crust, apple filling, etc.", recipe.getIngredients());
        assertEquals("... 5. Put in oven", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838296", recipe.getDateCreated());
        assertEquals("Lunch", recipe.getMealType());        
    }

    @Test
    void testFilterDinner() {

        recipeList.filter("Dinner");
        list = recipeList.getRecipeList();
        
        assertEquals(1, list.size());
        
        recipe = list.get(0);
        assertEquals("Lasagna", recipe.getRecipeName());
        assertEquals("pasta, meat, cheese", recipe.getIngredients());
        assertEquals("Step 1, Step 2, Step 3", recipe.getDirections());
        assertEquals("2023-12-01T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Dinner", recipe.getMealType());
    }

    // Integration Tests for Sort & Filter

    @Test
    void testAllAlphabetically() {

        recipeList.filter("All");
        recipeList.sortAlphabetically();
        list = recipeList.getRecipeList();

        assertEquals(3, list.size());

        recipe = list.get(0);
        assertEquals("PB+J Sandwich", recipe.getRecipeName());
        assertEquals("bread, peanut butter, jam", recipe.getIngredients());
        assertEquals("1. take the bread", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Breakfast", recipe.getMealType());

        recipe = list.get(1);
        assertEquals("Lasagna", recipe.getRecipeName());
        assertEquals("pasta, meat, cheese", recipe.getIngredients());
        assertEquals("Step 1, Step 2, Step 3", recipe.getDirections());
        assertEquals("2023-12-01T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Dinner", recipe.getMealType());

        recipe = list.get(2);
        assertEquals("Apple Pie", recipe.getRecipeName());
        assertEquals("crust, apple filling, etc.", recipe.getIngredients());
        assertEquals("... 5. Put in oven", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838296", recipe.getDateCreated());
        assertEquals("Lunch", recipe.getMealType());
    }

    @Test
    void testBreakfastAlphabetically() {

        recipeList.filter("Breakfast");
        recipeList.sortAlphabetically();
        list = recipeList.getRecipeList();

        assertEquals(1, list.size());

        recipe = list.get(0);
        assertEquals("PB+J Sandwich", recipe.getRecipeName());
        assertEquals("bread, peanut butter, jam", recipe.getIngredients());
        assertEquals("1. take the bread", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Breakfast", recipe.getMealType());
    }

    @Test
    void testLunchAlphabetically() {

        recipeList.filter("Lunch");
        recipeList.sortAlphabetically();
        list = recipeList.getRecipeList();

        assertEquals(1, list.size());

        recipe = list.get(0);
        assertEquals("Apple Pie", recipe.getRecipeName());
        assertEquals("crust, apple filling, etc.", recipe.getIngredients());
        assertEquals("... 5. Put in oven", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838296", recipe.getDateCreated());
        assertEquals("Lunch", recipe.getMealType());
    }

    @Test
    void testDinnerAlphabetically() {

        recipeList.filter("Dinner");
        recipeList.sortAlphabetically();
        list = recipeList.getRecipeList();

        assertEquals(1, list.size());

        recipe = list.get(0);
        assertEquals("Lasagna", recipe.getRecipeName());
        assertEquals("pasta, meat, cheese", recipe.getIngredients());
        assertEquals("Step 1, Step 2, Step 3", recipe.getDirections());
        assertEquals("2023-12-01T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Dinner", recipe.getMealType());
    }

    @Test
    void testAllReverseAlphabetically() {

        recipeList.filter("All");
        recipeList.sortReverseAlphabetically();
        list = recipeList.getRecipeList();

        assertEquals(3, list.size());

        recipe = list.get(0);
        assertEquals("Apple Pie", recipe.getRecipeName());
        assertEquals("crust, apple filling, etc.", recipe.getIngredients());
        assertEquals("... 5. Put in oven", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838296", recipe.getDateCreated());
        assertEquals("Lunch", recipe.getMealType());

        recipe = list.get(1);
        assertEquals("Lasagna", recipe.getRecipeName());
        assertEquals("pasta, meat, cheese", recipe.getIngredients());
        assertEquals("Step 1, Step 2, Step 3", recipe.getDirections());
        assertEquals("2023-12-01T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Dinner", recipe.getMealType());

        recipe = list.get(2);
        assertEquals("PB+J Sandwich", recipe.getRecipeName());
        assertEquals("bread, peanut butter, jam", recipe.getIngredients());
        assertEquals("1. take the bread", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Breakfast", recipe.getMealType());
    }

    @Test
    void testBreakfastReverseAlphabetically() {

        recipeList.filter("Breakfast");
        recipeList.sortReverseAlphabetically();
        list = recipeList.getRecipeList();

        assertEquals(1, list.size());

        recipe = list.get(0);
        assertEquals("PB+J Sandwich", recipe.getRecipeName());
        assertEquals("bread, peanut butter, jam", recipe.getIngredients());
        assertEquals("1. take the bread", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Breakfast", recipe.getMealType());
    }

    @Test
    void testLunchReverseAlphabetically() {

        recipeList.filter("Lunch");
        recipeList.sortReverseAlphabetically();
        list = recipeList.getRecipeList();

        assertEquals(1, list.size());

        recipe = list.get(0);
        assertEquals("Apple Pie", recipe.getRecipeName());
        assertEquals("crust, apple filling, etc.", recipe.getIngredients());
        assertEquals("... 5. Put in oven", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838296", recipe.getDateCreated());
        assertEquals("Lunch", recipe.getMealType());
    }

    @Test
    void testDinnerReverseAlphabetically() {

        recipeList.filter("Dinner");
        recipeList.sortReverseAlphabetically();
        list = recipeList.getRecipeList();

        assertEquals(1, list.size());

        recipe = list.get(0);
        assertEquals("Lasagna", recipe.getRecipeName());
        assertEquals("pasta, meat, cheese", recipe.getIngredients());
        assertEquals("Step 1, Step 2, Step 3", recipe.getDirections());
        assertEquals("2023-12-01T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Dinner", recipe.getMealType());
    }

    @Test
    void testAllNewest() {

        recipeList.filter("All");
        recipeList.sortNewest();
        list = recipeList.getRecipeList();

        assertEquals(3, list.size());

        recipe = list.get(0);
        assertEquals("Apple Pie", recipe.getRecipeName());
        assertEquals("crust, apple filling, etc.", recipe.getIngredients());
        assertEquals("... 5. Put in oven", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838296", recipe.getDateCreated());
        assertEquals("Lunch", recipe.getMealType());

        recipe = list.get(1);
        assertEquals("PB+J Sandwich", recipe.getRecipeName());
        assertEquals("bread, peanut butter, jam", recipe.getIngredients());
        assertEquals("1. take the bread", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Breakfast", recipe.getMealType());

        recipe = list.get(2);
        assertEquals("Lasagna", recipe.getRecipeName());
        assertEquals("pasta, meat, cheese", recipe.getIngredients());
        assertEquals("Step 1, Step 2, Step 3", recipe.getDirections());
        assertEquals("2023-12-01T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Dinner", recipe.getMealType());
    }

    @Test
    void testBreakfastNewest() {

        recipeList.filter("Breakfast");
        recipeList.sortNewest();
        list = recipeList.getRecipeList();

        assertEquals(1, list.size());

        recipe = list.get(0);
        assertEquals("PB+J Sandwich", recipe.getRecipeName());
        assertEquals("bread, peanut butter, jam", recipe.getIngredients());
        assertEquals("1. take the bread", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Breakfast", recipe.getMealType());
    }

    @Test
    void testLunchNewest() {

        recipeList.filter("Lunch");
        recipeList.sortNewest();
        list = recipeList.getRecipeList();

        assertEquals(1, list.size());

        recipe = list.get(0);
        assertEquals("Apple Pie", recipe.getRecipeName());
        assertEquals("crust, apple filling, etc.", recipe.getIngredients());
        assertEquals("... 5. Put in oven", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838296", recipe.getDateCreated());
        assertEquals("Lunch", recipe.getMealType());
    }

    @Test
    void testDinnerNewest() {

        recipeList.filter("Dinner");
        recipeList.sortNewest();
        list = recipeList.getRecipeList();

        assertEquals(1, list.size());

        recipe = list.get(0);
        assertEquals("Lasagna", recipe.getRecipeName());
        assertEquals("pasta, meat, cheese", recipe.getIngredients());
        assertEquals("Step 1, Step 2, Step 3", recipe.getDirections());
        assertEquals("2023-12-01T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Dinner", recipe.getMealType());
    }

    @Test
    void testAllOldest() {

        recipeList.filter("All");
        recipeList.sortOldest();
        list = recipeList.getRecipeList();

        assertEquals(3, list.size());

        recipe = list.get(0);
        assertEquals("Lasagna", recipe.getRecipeName());
        assertEquals("pasta, meat, cheese", recipe.getIngredients());
        assertEquals("Step 1, Step 2, Step 3", recipe.getDirections());
        assertEquals("2023-12-01T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Dinner", recipe.getMealType());

        recipe = list.get(1);
        assertEquals("PB+J Sandwich", recipe.getRecipeName());
        assertEquals("bread, peanut butter, jam", recipe.getIngredients());
        assertEquals("1. take the bread", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Breakfast", recipe.getMealType());

        recipe = list.get(2);
        assertEquals("Apple Pie", recipe.getRecipeName());
        assertEquals("crust, apple filling, etc.", recipe.getIngredients());
        assertEquals("... 5. Put in oven", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838296", recipe.getDateCreated());
        assertEquals("Lunch", recipe.getMealType());
    }

    @Test
    void testBreakfastOldest() {

        recipeList.filter("Breakfast");
        recipeList.sortOldest();
        list = recipeList.getRecipeList();

        assertEquals(1, list.size());

        recipe = list.get(0);
        assertEquals("PB+J Sandwich", recipe.getRecipeName());
        assertEquals("bread, peanut butter, jam", recipe.getIngredients());
        assertEquals("1. take the bread", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Breakfast", recipe.getMealType());
    }

    @Test
    void testLunchOldest() {

        recipeList.filter("Lunch");
        recipeList.sortOldest();
        list = recipeList.getRecipeList();

        assertEquals(1, list.size());

        recipe = list.get(0);
        assertEquals("Apple Pie", recipe.getRecipeName());
        assertEquals("crust, apple filling, etc.", recipe.getIngredients());
        assertEquals("... 5. Put in oven", recipe.getDirections());
        assertEquals("2023-11-19T21:42:32.838296", recipe.getDateCreated());
        assertEquals("Lunch", recipe.getMealType());
    }

    @Test
    void testDinnerOldest() {
        recipeList.filter("Dinner");
        recipeList.sortOldest();
        list = recipeList.getRecipeList();

        assertEquals(1, list.size());

        recipe = list.get(0);
        assertEquals("Lasagna", recipe.getRecipeName());
        assertEquals("pasta, meat, cheese", recipe.getIngredients());
        assertEquals("Step 1, Step 2, Step 3", recipe.getDirections());
        assertEquals("2023-12-01T21:42:32.838297", recipe.getDateCreated());
        assertEquals("Dinner", recipe.getMealType());
    }
}