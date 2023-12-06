package FrontEnd;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

class LoginPageHeader extends HBox {

    LoginPageHeader() {

        this.setPrefSize(Constants.WINDOW_WIDTH, 100); // Size of the header
        this.setStyle(Constants.boldBackgroundColor);

        Text titleText = new Text("PantryPal"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 80;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }
}

class LoginPageFooter extends HBox {

    private Button LoginButton;

    LoginPageFooter() {
        this.setPrefSize(Constants.WINDOW_WIDTH, 100);
        this.setStyle(Constants.boldBackgroundColor);
        this.setSpacing(15);      

        LoginButton = new Button("Login"); // text displayed on add button
        LoginButton.setStyle(Constants.defaultButtonStyle); // styling the button

        this.getChildren().addAll(LoginButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center

    }

    public Button getLoginButton() {
        return LoginButton;
    }

}

class UsernameInfo extends VBox {
    private Label Username;
    private TextField username;

    UsernameInfo(){
        Username = new Label("Username");
        int width = 500;
        Username.setMaxWidth(width);
        Username.setStyle("-fx-font-size: 18; -fx-text-fill: green;"+"-fx-font-size: 35;");

        username = new TextField();
        username.setPrefSize(300, 60); // set size of text field
        username.setPadding(new Insets(10, 80, 0, 80));
        username.setStyle("-fx-background-color: #DAE5EA; -fx-background-insets: 0 75 0 75; -fx-border-width: 0;");


        this.getChildren().addAll(Username,username);
        this.setAlignment(Pos.CENTER);
    }

    public String getUsername() {
        return username.getText();
    }
}

class PasswordInfo extends VBox {
    private Label Password;
    private TextField password;

    PasswordInfo(){
        Password = new Label("Password");
        int width = 500;
        Password.setMaxWidth(width);
        Password.setStyle("-fx-font-size: 18; -fx-text-fill: green;"+"-fx-font-size: 35;");

        password = new TextField();
        password.setPrefSize(300, 60); // set size of text field
        password.setPadding(new Insets(10, 80, 0, 80));
        password.setStyle("-fx-background-color: #DAE5EA; -fx-background-insets: 0 75 0 75; -fx-border-width: 0;"); 

        this.getChildren().addAll(Password,password);
        this.setAlignment(Pos.CENTER);
    }

    public String getPassword() {
        return password.getText();
    }
}

class LoginAccountInfo extends VBox {

    private Button CreateAccountButton;
    private UsernameInfo username;
    private PasswordInfo password;

    private CheckBox rememberMe;

    LoginAccountInfo() {
        this.setPrefSize(Constants.WINDOW_WIDTH, 100);
        this.setStyle(Constants.boldBackgroundColor);
        this.setSpacing(15);      

        username = new UsernameInfo();
        password = new PasswordInfo();
        rememberMe = new CheckBox("Remember This Login");
        rememberMe.setStyle(Constants.defaultTextStyle);
        VBox.setMargin(password, new Insets(60, 0, 0, 0));

        CreateAccountButton = new Button("Go to Create Account"); // text displayed on add button
        CreateAccountButton.setStyle(Constants.defaultButtonStyle); // styling the button
        CreateAccountButton.setWrapText(true);
        VBox.setMargin(CreateAccountButton, new Insets(50, 0, 0, 0));


        this.getChildren().addAll(username,password,rememberMe,CreateAccountButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center
        this.setSpacing(10);

    }

    public Button getCreateAccountButton() {
        return CreateAccountButton;
    }
    public String getName() {
        return username.getUsername();
    }
    public String getPassword() {
        return password.getPassword();
    }

    public boolean getRememberMeBoolean(){
        return rememberMe.isSelected();
    }
}


public class LoginPageFrame extends BorderPane{

    /**
     * Declare Scene Elements Here
     */
    private LoginPageHeader header;
    private LoginPageFooter footer;
    private LoginAccountInfo info;
    private Label loginLabel;
    private String username;
    private String password;
    private VBox headercomplete;

    //private String userId;

    Stage stage;

    /**
     * Declare Scene Buttons Here
     */
    private Button AccountCreationButton;
    private Button LoginButton;

    public String getUsername(){
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LoginAccountInfo getInfo() {
        return info;
    }

    public Stage getStage() {
        return stage;
    }

    public Button getLoginButton() {
        return LoginButton;
    }

    public Button getAccountCreationButton() {
        return AccountCreationButton;
    }
 
    LoginPageFrame()
    {
        /**
         * Initialize / Assign Elements Here
         */
        //stage = new Stage();
        header = new LoginPageHeader();
        footer = new LoginPageFooter();
        info = new LoginAccountInfo();
        loginLabel = new Label("Login:");
        loginLabel.setStyle(Constants.defaultTextStyle);
        loginLabel.setPadding(new Insets(10));

        username = info.getName();
        password = info.getPassword();

        headercomplete = new VBox();
        headercomplete.setStyle(Constants.defaultBackgroundColor);
        headercomplete.getChildren().addAll(header, loginLabel);

        LoginButton = footer.getLoginButton();
        AccountCreationButton = info.getCreateAccountButton();

        /**
         * Set element positions here
         */
        this.setTop(headercomplete);
        this.setCenter(info);
        this.setBottom(footer);
        this.requestLayout();

    }

    public void setAccountCreationButtonAction(EventHandler<ActionEvent> eventHandler) {
        AccountCreationButton.setOnAction(eventHandler);
    }

    public void setLoginButtonAction(EventHandler<ActionEvent> eventHandler) {
        LoginButton.setOnAction(eventHandler);
    }
    
}