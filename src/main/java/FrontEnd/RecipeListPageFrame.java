package FrontEnd;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

class RecipeListPageHeader extends HBox {

    private Button logoutButton;

    RecipeListPageHeader() {
        this.setPrefSize(Constants.WINDOW_WIDTH, 100); // Size of the header
        this.setStyle(Constants.boldBackgroundColor);

        Region r = new Region();
        r.setPrefSize(50, 100);
        logoutButton = new Button("Logout");
        logoutButton.setStyle(Constants.loginButtonStyle);
        Text titleText = new Text("PantryPal"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 80;");
        this.getChildren().addAll(titleText, r, logoutButton);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }

    public Button getLogoutButton(){
        return logoutButton;
    }
}

class RecipeListPageFooter extends HBox {

    private Button newRecipeButton;

    RecipeListPageFooter() {
        this.setPrefSize(Constants.WINDOW_WIDTH, 100);
        this.setStyle(Constants.boldBackgroundColor);
        this.setSpacing(15);      

        newRecipeButton = new Button("New Recipe"); // text displayed on add button
        newRecipeButton.setStyle(Constants.defaultButtonStyle); // styling the button

        this.getChildren().addAll(newRecipeButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center

    }

    public Button getNewRecipeButton() {
        return newRecipeButton;
    }

}

public class RecipeListPageFrame extends BorderPane{

    /**
     * Declare Scene Elements Here
     */
    private RecipeListPageHeader header;
    private RecipeListPageFooter footer;
    private VBox recipeListComplete;
    private ScrollPane recipeListScrollPane;
    private Label recipeListLabel;
    private RecipeListDisplay recipeList;
    //private RecipeListData recipeListData; Not used
    private RecipeListDisplay reversedList;
    private HBox buttonMenu;
    private AnchorPane left;
    private AnchorPane middle;
    private AnchorPane right;

    /**
     * Declare Scene Buttons Here
     */
    Button newRecipeButton;
    Button logoutButton;

    // getter
    public Button getNewRecipeButton() {
        return newRecipeButton;
    }
    public RecipeListDisplay getRecipeList() {
        return recipeList;
    }
    public RecipeListDisplay getReversedList() {
        return reversedList;
    }
    String sortMenuName;
    String filterMenuName;

    public Button getlogoutButton() {
        return logoutButton;
    }

    public String getSortMenuName() {
        return sortMenuName;
    }

    public String getFilterMenuName() {
        return filterMenuName;
    }

    // Declare Sort Dropdown
    MenuButton sortButton;
    MenuItem alphaOpt;
    MenuItem reverseOpt;
    MenuItem newOpt;
    MenuItem oldOpt;

    public MenuItem getAlphaOpt() {
        return alphaOpt;
    }

    public MenuItem getReverseOpt() {
        return reverseOpt;
    }

    public MenuItem getNewOpt() {
        return newOpt;
    }

    public MenuItem getOldOpt() {
        return oldOpt;
    }
    // Declare Filter Dropdown
    MenuButton filterButton;
    MenuItem all;
    MenuItem breakfast;
    MenuItem lunch;
    MenuItem dinner;

    public MenuItem getAll() {
        return all;
    }

    public MenuItem getBreakfast() {
        return breakfast;
    }

    public MenuItem getLunch() {
        return lunch;
    }

    public MenuItem getDinner() {
        return dinner;
    }

    RecipeListPageFrame(){
        this("Sort", "Filter", "storage.json");
    }

    RecipeListPageFrame(String sortMenu, String filterMenu, String fileName)
    {

        sortMenuName = sortMenu;
        filterMenuName = filterMenu;

        /**
         * Initialize / Assign Elements Here
         */
        header = new RecipeListPageHeader();
        footer = new RecipeListPageFooter();
        recipeList = new RecipeListDisplay(fileName); //default constructor reads .json file
        recipeListComplete = new VBox();
        buttonMenu = new HBox();
        right = new AnchorPane();
        middle = new AnchorPane();
        left = new AnchorPane();

        // Initialize Sort DropDown
        sortButton = new MenuButton(sortMenu);
        alphaOpt = new MenuItem("A-Z");
        reverseOpt = new MenuItem("Z-A");
        newOpt = new MenuItem("Newest");
        oldOpt = new MenuItem("Oldest");
        sortButton.getItems().addAll(alphaOpt, reverseOpt, newOpt, oldOpt);
        sortButton.setStyle(Constants.defaultButtonStyle);

        // Initialize Filter Dropdown
        filterButton = new MenuButton(filterMenu);
        all = new MenuItem("All");
        breakfast = new MenuItem("Breakfast");
        lunch = new MenuItem("Lunch");
        dinner = new MenuItem("Dinner");
        filterButton.getItems().addAll(all, breakfast, lunch, dinner);
        filterButton.setStyle(Constants.defaultButtonStyle);

        recipeListLabel = new Label("Recipe List:");
        recipeListLabel.setStyle(Constants.defaultTextStyle);
        recipeListLabel.setPadding(new Insets(10));
        reversedList = new RecipeListDisplay(recipeList); //Uses new RecipeList constructor to reverse the order

        // Align Menu Bar Elements
        left.getChildren().add(recipeListLabel);
        HBox.setHgrow(left, Priority.ALWAYS);
        middle.getChildren().add(sortButton);
        right.getChildren().add(filterButton);

        recipeListScrollPane = new ScrollPane(reversedList);
        recipeListScrollPane.setFitToWidth(true);
        recipeListScrollPane.setFitToHeight(true);

        // Populate Menu
        buttonMenu.setPrefSize(Constants.WINDOW_WIDTH, 10);
        buttonMenu.getChildren().addAll(left, middle, right);

        recipeListComplete.setStyle(Constants.defaultBackgroundColor);
        recipeListComplete.getChildren().addAll(buttonMenu, recipeListScrollPane);
        
        newRecipeButton = footer.getNewRecipeButton();
        logoutButton = header.getLogoutButton();

        /**
         * Set element positions here
         */
        this.setTop(header);
        this.setCenter(recipeListComplete);
        this.setBottom(footer);

        //Add button listeners
    }

    public MenuButton getSortButton() {
        return sortButton;
    }

    public void setlogoutButtonAction(EventHandler<ActionEvent> eventHandler) {
        logoutButton.setOnAction(eventHandler);
    }

    public void setNewRecipeButtonAction(EventHandler<ActionEvent> eventHandler) {
        newRecipeButton.setOnAction(eventHandler);
    }
    
    public void setAlphaOptAction(EventHandler<ActionEvent> eventHandler) {
        alphaOpt.setOnAction(eventHandler);
    }

    public void setReverseOptAction(EventHandler<ActionEvent> eventHandler) {
        reverseOpt.setOnAction(eventHandler);
    }

    public void setNewOptAction(EventHandler<ActionEvent> eventHandler) {
        newOpt.setOnAction(eventHandler);
    }

    public void setOldOptAction(EventHandler<ActionEvent> eventHandler) {
        oldOpt.setOnAction(eventHandler);
    }

    public void setAllAction(EventHandler<ActionEvent> eventHandler) {
        all.setOnAction(eventHandler);
    }

    public void setBreakfastAction(EventHandler<ActionEvent> eventHandler) {
        breakfast.setOnAction(eventHandler);
    }

    public void setLunchAction(EventHandler<ActionEvent> eventHandler) {
        lunch.setOnAction(eventHandler);
    }

    public void setDinnerAction(EventHandler<ActionEvent> eventHandler) {
        dinner.setOnAction(eventHandler);
    }

}