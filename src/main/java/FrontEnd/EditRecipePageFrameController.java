package FrontEnd;

import javax.swing.text.View;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditRecipePageFrameController {
    EditRecipePageFrame view;

    public EditRecipePageFrameController(EditRecipePageFrame view, Model model) {
        this.view = view;
        this.model = model;

        this.view.setSaveButtonAction(this::handleNewSaveButton);
    }

    private void handleNewSaveButton(ActionEvent event) {
        	// saves changes and returns to detailed view
            
        	view.getRecipe().setRecipeName(view.getDetails().getRecipeName());
        	view.getRecipe().setIngredients(view.getDetails().getIngredients());
        	view.getRecipe().setDirections(view.getDetails().getDirections());
        	
        	//open recipe details page with updated info
        	/**ViewRecipePageFrame ViewRecipePage = new ViewRecipePageFrame(this.recipe, stage);
        	stage.setScene(new Scene(ViewRecipePage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        	stage.setResizable(false);
        	stage.show();*/
            view.getParen().refresh();
        	
        	//update RecipeList/file:  with new recipe list
            JSONSaver.updateJSON(view.getOriginalRecipeName(), view.getRecipe());

        	
        	view.getNewStage().close();//close edit page
    }

    private void handleNewBackButton(ActionEvent event) {
        view.getNewStage().close();
    }
}
