package FrontEnd;

import java.io.File;

import javax.swing.text.View;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RecipeListPageFrameController implements Controller {
    private RecipeListPageFrame view;
    private HTTPRequestModel model;

    // need to create model
    // private Model model;
    public RecipeListPageFrameController(RecipeListPageFrame view, HTTPRequestModel model) {
        this.view = view;
        this.model = model;

        this.view.setNewRecipeButtonAction(this::handleNewRecipeButton);
        this.view.setlogoutButtonAction(this::handlelogoutButton);
    }

    private void handleNewRecipeButton(ActionEvent event) {
        Stage stage = (Stage) view.getNewRecipeButton().getScene().getWindow();
        NewRecipePageFrame NewRecipePage = new NewRecipePageFrame(stage, view.getRecipeList(), view.getReversedList());
        NewRecipePageFrameController newRepPageCon = new NewRecipePageFrameController(NewRecipePage, model);
        Main.setController(newRepPageCon);
        stage.setScene(new Scene(NewRecipePage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        stage.setResizable(false);
        stage.show();
    }

    private void handlelogoutButton(ActionEvent event) {
        try {
            File userFile = new File("user.txt");
            userFile.delete();
        } catch (Exception fileException) {
            fileException.printStackTrace();
        }

        Stage stage = (Stage) view.getlogoutButton().getScene().getWindow();
        LoginPageFrame newLoginPage = new LoginPageFrame();
        Controller frontPController = new LoginPageFrameController(newLoginPage, model);
        Main.setController(frontPController);
        stage.setScene(new Scene(newLoginPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        stage.setResizable(false);
        stage.show();
    }

}
