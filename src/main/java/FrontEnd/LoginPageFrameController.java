package FrontEnd;

import javafx.event.ActionEvent;

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

    }

    private void handleLoginButton(ActionEvent event) {

    }
}
