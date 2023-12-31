package FrontEnd;


import javafx.event.ActionEvent;

public class EditRecipePageFrameController implements Controller{
    EditRecipePageFrame view;
    HTTPRequestModel model;

    public EditRecipePageFrameController(EditRecipePageFrame view, HTTPRequestModel model) {
        this.view = view;
        this.model = model;

        this.view.setSaveButtonAction(this::handleNewSaveButton);
        this.view.setBackButtonAction(this::handleNewBackButton);
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
            String success = model.performRecipeListPOSTRequest();

            if(success.equals("Success!")){
                
            }
        	
        	view.getNewStage().close();//close edit page
    }

    private void handleNewBackButton(ActionEvent event) {
        // might need to add controller to go back here...
        // so when we click view, is a new controller set?
        // it should set to this controller...so we should have a new controlelr set to something
        // else
        view.getNewStage().close();
    }
}
