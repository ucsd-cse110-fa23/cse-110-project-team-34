package FrontEnd;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

public class SharePopup extends BorderPane{
    
    /**
     * Declare Scene Elements Here
     */
    private Label shareLabel;
    private TextField urlField;


    /**
     * Declare Scene Buttons Here
     */
    Button closeButton;

    SharePopup(String recipeName)
    {
        /**
         * Initialize / Assign Elements Here
         */
        shareLabel = new Label("Share URL");
        shareLabel.setStyle(Constants.defaultTextStyle);
        shareLabel.setPadding(new Insets(10));
        
        urlField = new TextField("http://localhost:8100/share?userID=" + UserID.userID + "&recipeName=" + recipeName);
        urlField.setEditable(false);
        urlField.setStyle("");

        closeButton = new Button("Close");

        /**
         * Set element positions here
         */
        this.setTop(shareLabel);
        this.setCenter(urlField);
        this.setBottom(closeButton);


        //Add button listeners
        addListeners();
    }

    public void addListeners()
    {
        // Add button functionality (just changes the Stage to the NewRecipePageFrame)
        closeButton.setOnAction(e -> {
            Stage popup = (Stage) closeButton.getScene().getWindow();
            popup.close();
        });


    }
}
