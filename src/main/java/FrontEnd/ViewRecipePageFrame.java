package FrontEnd;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

class ViewRecipePageHeader extends HBox {

    private Label titleText;
	
	ViewRecipePageHeader(Recipe recipe) {
        this.setPadding(new Insets(20));

		this.setPrefSize(Constants.WINDOW_WIDTH, 150); // Size of the header
        this.setStyle(Constants.boldBackgroundColor);
        
        titleText = new Label(recipe.getRecipeName()); // Text of the Header
        titleText.setStyle(Constants.defaultRecipeTitle);
        titleText.setWrapText(true);

        
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
	}

    public void refresh(Recipe rec){
        this.titleText.setText(rec.getRecipeName());
    }
}

class ViewRecipe extends VBox {
    private Label ingredientsLabel;
    private Region r;
    private Label directionsLabel;

    ViewRecipe(Recipe recipe) {
        this.setPadding(new Insets(50));

        Image image = new Image(recipe.getImg());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(400);
        imageView.setFitHeight(275);

        ingredientsLabel = new Label("Ingredients: " + recipe.getIngredients());
        ingredientsLabel.setWrapText(true);
        ingredientsLabel.setStyle(Constants.defaultTextStyle);

        r = new Region();
        r.setPrefSize(Constants.WINDOW_WIDTH, 50);

        directionsLabel = new Label("Directions: " + recipe.getDirections());
        directionsLabel.setWrapText(true);
        directionsLabel.setStyle(Constants.defaultTextStyle);
        this.getChildren().addAll(imageView, ingredientsLabel, r, directionsLabel);
        this.setAlignment(Pos.CENTER);
    }

    public void refresh(Recipe rec){
        this.ingredientsLabel.setText("Ingredients: " + rec.getIngredients());
        this.directionsLabel.setText("Directions: " + rec.getDirections());
    }
}

class ViewRecipePageFooter extends HBox {
	private Button BackButton;
	private Button EditButton;
	private Button DeleteButton;
    private Button shareButton;
	
	ViewRecipePageFooter() {
		this.setPrefSize(Constants.WINDOW_WIDTH, 100);
        this.setStyle(Constants.boldBackgroundColor);
        this.setSpacing(15);
        
        BackButton = new Button("Back"); // text displayed on add button
        BackButton.setStyle(Constants.defaultButtonStyle); // styling the button

        EditButton = new Button("Edit"); // text displayed on add button
        EditButton.setStyle(Constants.defaultButtonStyle);
        
        DeleteButton = new Button("Delete"); // text displayed on add button
        DeleteButton.setStyle(Constants.defaultButtonStyle);

        shareButton = new Button("Share"); // text displayed on add button
        shareButton.setStyle(Constants.defaultButtonStyle);

        this.getChildren().addAll(BackButton, EditButton, DeleteButton, shareButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center
	}
	
	public Button getBackButton() {
        return BackButton;
    }
	
	public Button getEditButton() {
        return EditButton;
    }
	
	public Button getDeleteButton() {
        return DeleteButton;
    }

    public Button getShareButton(){
        return shareButton;
    }
	
}

public class ViewRecipePageFrame extends BorderPane {
	
	/**
     * Declare Scene Elements Here
     */
    private ViewRecipePageHeader header;
    private ViewRecipePageFooter footer;
    private ScrollPane scrollPane;
    private ViewRecipe details;
    private Recipe recipe;
    private Stage stage;
    
    /**
     * Declare Scene Buttons Here
     */
    Button newBackButton;
    Button newEditButton;
    Button newDeleteButton;
    Button shareButton;

	public Button getBackButton() {
        return newBackButton;
    }
	
	public Button getEditButton() {
        return newEditButton;
    }
	
	public Button getDeleteButton() {
        return newDeleteButton;
    }

    public Button getShareButton(){
        return shareButton;
    }

    public Stage getStage() {
        return stage;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    
    ViewRecipePageFrame(Recipe recipe, Stage stage) {
    	
    	/**
         * Initialize / Assign Elements Here
         */
    	this.recipe = recipe;
    	this.stage = stage;
    	header = new ViewRecipePageHeader(this.recipe);
    	footer = new ViewRecipePageFooter();
    	details = new ViewRecipe(this.recipe);
    	
    	scrollPane = new ScrollPane(details);
        scrollPane.setFitToWidth(true);
        // scrollPane.setFitToHeight(true);
        
        shareButton = footer.getShareButton();
        newBackButton = footer.getBackButton();
        newEditButton = footer.getEditButton();
        newDeleteButton = footer.getDeleteButton();
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(header);

        /**
         * Set element positions here
         */
        this.setTop(vBox);
        this.setCenter(scrollPane);
        this.setBottom(footer);

    }

    public void setBackButtonAction(EventHandler<ActionEvent> eventHandler) {
        newBackButton.setOnAction(eventHandler);
    }

    public void setEditButtonAction(EventHandler<ActionEvent> eventHandler) {
        newEditButton.setOnAction(eventHandler);
    }

    public void setDeleteButtonAction(EventHandler<ActionEvent> eventHandler) {
        newDeleteButton.setOnAction(eventHandler);
    }

    public void setShareButtonAction(EventHandler<ActionEvent> eventHandler){
        shareButton.setOnAction(eventHandler);
    }
    
    public void refresh(){
        this.header.refresh(this.recipe);
        this.details.refresh(this.recipe);
    }
}