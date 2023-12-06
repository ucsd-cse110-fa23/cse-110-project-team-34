package FrontEnd;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ViewRecipePageFrameController {
    ViewRecipePageFrame view;
    HTTPRequestModel model;

    public ViewRecipePageFrameController(ViewRecipePageFrame view, HTTPRequestModel model) {
        this.view = view;
        this.model = model;

        this.view.setBackButtonAction(this::handleBackButton);
        this.view.setEditButtonAction(this::handleEditButton);
        this.view.setDeleteButtonAction(this::handleDeleteButton);
        this.view.setShareButtonAction(this::handleShareButton);
    }

    private void handleShareButton(ActionEvent event) {
        Stage popupStage = new Stage();
        popupStage.setTitle("Share");
        popupStage.setScene(new Scene(new SharePopup(view.getRecipe().getRecipeName()), Constants.SHAREWINDOWWIDTH, Constants.SHAREWINDOWHEIGHT));
        popupStage.show();
    }

    private void handleBackButton(ActionEvent event) {
        RecipeListPageFrame recipeListPage = new RecipeListPageFrame();
        RecipeListPageFrameController recipeListPageController = new RecipeListPageFrameController(recipeListPage, model);
        Main.setController(recipeListPageController);
        view.getStage().setTitle("PantryPal");
        view.getStage().getIcons().add(new Image(Constants.defaultIconPath));
        view.getStage().setScene(new Scene(recipeListPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        view.getStage().setResizable(false);
        view.getStage().show();
    }

    private void handleEditButton(ActionEvent event) {
        Stage newStage = new Stage();
        Recipe recipe = view.getRecipe();
        EditRecipePageFrame editRecipePage = new EditRecipePageFrame(recipe, newStage, view);
        EditRecipePageFrameController controller = new EditRecipePageFrameController(editRecipePage, model);
        newStage.setScene(new Scene(editRecipePage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        newStage.setResizable(false);
        newStage.showAndWait();
    }

    private void handleDeleteButton(ActionEvent event) {
        // deletes recipe
        JSONSaver.removeByName(view.getRecipe().getRecipeName());

        // Saves change to server
       // HTTPRequestModel httpRequestModel = new HTTPRequestModel(); // TODO: Remove when controller is implemented
        String response = model.performRecipeListPOSTRequest();

        // returns to recipe list page
        // I just did what was there for back button. This is quite jank tbh
        RecipeListPageFrame recipeListPage = new RecipeListPageFrame();
        RecipeListPageFrameController recipeListPageController = new RecipeListPageFrameController(recipeListPage, model);
        Main.setController(recipeListPageController);
        view.getStage().setTitle("PantryPal");
        view.getStage().getIcons().add(new Image(Constants.defaultIconPath));
        view.getStage().setScene(new Scene(recipeListPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        view.getStage().setResizable(false);
        view.getStage().show();
    }
}
