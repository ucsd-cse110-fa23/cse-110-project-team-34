package FrontEnd;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class ErrorSys {
    
    public static void quickErrorPopup(String errorMessage){
        Alert alert = new Alert(AlertType.ERROR, errorMessage, ButtonType.OK);
        alert.showAndWait();
    }

}
