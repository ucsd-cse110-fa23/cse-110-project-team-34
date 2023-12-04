package FrontEnd;

import javax.swing.text.View;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RecipeListPageFrameController {
    private RecipeListPageFrame view;
    private HTTPRequestModel model;

    // need to create model
    //private Model model;
    public RecipeListPageFrameController(RecipeListPageFrame view, HTTPRequestModel model) {
        this.view = view;
        this.model = model;

        this.view.setNewRecipeButtonAction(this::handleNewRecipeButton);
    }

    private void handleNewRecipeButton(ActionEvent event) {
        Stage stage = (Stage) view.getNewRecipeButton().getScene().getWindow();
        NewRecipePageFrame NewRecipePage = new NewRecipePageFrame(stage, view.getRecipeList(), view.getReversedList());
        stage.setScene(new Scene(NewRecipePage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        stage.setResizable(false);
        stage.show();
        // String response = model does something;
    }
}
