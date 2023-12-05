package FrontEnd;

import java.io.File;
import java.io.FileWriter;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginPageFrameController {
    private LoginPageFrame view;
    private HTTPRequestModel model;

    public LoginPageFrameController(LoginPageFrame view, HTTPRequestModel model) {
        this.view = view;
        this.model = model;

        this.view.setAccountCreationButtonAction(this::handleAccountCreationButton);
        this.view.setLoginButtonAction(this::handleLoginButton);

    }

    private void handleAccountCreationButton(ActionEvent event) {
        // this method creates a button inside... how to refactor that?
    }

    private void handleLoginButton(ActionEvent event) {
        // should be okay to use this instead of setter methods
        String username = view.getUsername();
        String password = view.getPassword();

        username = view.getInfo().getName();
        password = view.getInfo().getPassword();

        // HTTPRequestModel testModel = new HTTPRequestModel(); 

        String userID = model.performLoginRequest(username, password);

        if (userID != null) {

            if (view.getInfo().getRememberMeBoolean()) {
                try {
                    FileWriter fw = new FileWriter(new File("user.txt"));
                    fw.write(userID);
                    fw.close();
                } catch (Exception rememberException) {
                    rememberException.printStackTrace();
                }

            }

            // Set program UserID
            UserID.setUserID(userID);
            Stage stage = view.getStage();
            // Go to recipe list page
            stage = (Stage) view.getLoginButton().getScene().getWindow();
            RecipeListPageFrame frontPage = new RecipeListPageFrame();
            stage.setTitle("PantryPal");
            stage.getIcons().add(new Image(Constants.defaultIconPath));
            stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();

        } else {
            ErrorSys.quickErrorPopup("Login Failed");
        }
    }
}
