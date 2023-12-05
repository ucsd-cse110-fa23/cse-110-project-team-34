package FrontEnd;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class NewRecipePageFrameController implements Controller{

    NewRecipePageFrame view;
    HTTPRequestModel model;

    public NewRecipePageFrameController(NewRecipePageFrame view, HTTPRequestModel model) {
        this.view = view;
        this.model = model;

        this.view.setNewBackButtonAction(this::handleNewBackButton);
        this.view.setNewSaveButtonAction(this::handleNewSaveButton);
        this.view.setNewGenerateButtonAction(null);
        this.view.setRecordButtonAction(null);
        this.view.setBreakfastButtonAction(null);
        this.view.setLunchButtonAction(null);
        this.view.setDinnerButtonAction(null);
    }

    public void handleNewBackButton(ActionEvent event) {
            RecipeListPageFrame recipeListPage = new RecipeListPageFrame();
            RecipeListPageFrameController recipeListPageController = new RecipeListPageFrameController(recipeListPage, model);
            Main.setController(recipeListPageController);
            view.getStage().setTitle("PantryPal");
            view.getStage().getIcons().add(new Image(Constants.defaultIconPath));
            view.getStage().setScene(new Scene(recipeListPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            view.getStage().setResizable(false);
            view.getStage().show();
    }

    public void handleNewSaveButton(ActionEvent event) {

            //add recipe to recipeList
            view.getList().getChildren().add(new RecipeSimple(view.getRecipe()));
            view.getReverse().getChildren().add(0, new RecipeSimple((view.getRecipe())));
            //save to .json
            JSONSaver.saveRecipeList(view.getList());
            
            String response = model.performRecipeListPOSTRequest();

            //sort tasks, tasks are added at end, just show by reverse order (for loop starting at the end)
    }

    public void handleNewGenerateButton(ActionEvent event) {
        
    }

    public void handleRecordButton(ActionEvent event) {

    }

    public void handleBreakfastButton(ActionEvent event) {

    }

    public void handleLunchButton(ActionEvent event) {

    }

    public void handleDinnerButton(ActionEvent event) {

    }

}

