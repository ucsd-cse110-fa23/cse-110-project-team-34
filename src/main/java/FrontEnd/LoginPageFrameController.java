package FrontEnd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginPageFrameController implements Controller {
    private LoginPageFrame view;
    private HTTPRequestModel model;

    public LoginPageFrameController(LoginPageFrame view, HTTPRequestModel model) {
        this.view = view;
        this.model = model;
        this.view.setAccountCreationButtonAction(this::handleAccountCreationButton);
        this.view.setLoginButtonAction(this::handleLoginButton);

    }

    private void handleAccountCreationButton(ActionEvent event) {
        Stage newStage = new Stage();
        newStage.setTitle("Create Account");

        TextField Username = new TextField("");// "Enter Username");
        TextField Password = new TextField("");// "Enter Password");
        TextField ComfirmPassword = new TextField("");// "Re-enter Password");

        Button CreateAccount = new Button("Create Account");
        CreateAccount.setOnAction(e -> { // add CreateButton functionality
            String NameText = Username.getText();
            String PasswordText = Password.getText();
            String Re_Entered_PasswordText = ComfirmPassword.getText();

            //HTTPRequestModel httpRequestModel = new HTTPRequestModel(); // TODO: Remove this when controllers
                                                                        // implemented

            if (!PasswordText.equals(Re_Entered_PasswordText)) {
                ErrorSys.quickErrorPopup("Password and Retyped Password Do Not Match");
            } else {
                String response = model.performSignupRequest(NameText, PasswordText);
                if (response.equals("Username Already Exists")) {
                    ErrorSys.quickErrorPopup("Username Already Exists");
                } else if (response.equals("Account Successfully Created!")) {
                    newStage.close();
                    Alert alert = new Alert(AlertType.INFORMATION, "Account Successfully Created!", ButtonType.OK);
                    alert.showAndWait();
                }
            }

        });

        // Create layout for new page
        VBox AccountDetail = new VBox(10);
        AccountDetail.getChildren().addAll(
                new Label("Username:"),
                Username,
                new Label("Password:"),
                Password,
                new Label("Re-enter Password:"),
                ComfirmPassword,
                CreateAccount);
        AccountDetail.setStyle("-fx-font-size: 18;");
        // Set scene
        Scene CreateAccountScene = new Scene(AccountDetail, 500, 600);
        newStage.setScene(CreateAccountScene);
        newStage.show();
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
            RecipeListPageFrame recipeListPage = new RecipeListPageFrame();
            Controller c1 = new RecipeListPageFrameController(recipeListPage, model);
            Main.setController(c1);
            stage.setTitle("PantryPal");
            stage.getIcons().add(new Image(Constants.defaultIconPath));
            stage.setScene(new Scene(recipeListPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();

        } else {
            ErrorSys.quickErrorPopup("Login Failed");
        }
    }
}
