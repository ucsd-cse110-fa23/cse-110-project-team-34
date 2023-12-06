package FrontEnd;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
    private RecipeListData recipeListData;
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

    // Declare Sort Dropdown
    MenuButton sortButton;
    MenuItem alphaOpt;
    MenuItem reverseOpt;
    MenuItem newOpt;
    MenuItem oldOpt;

    // Declare Filter Dropdown
    MenuButton filterButton;
    MenuItem all;
    MenuItem breakfast;
    MenuItem lunch;
    MenuItem dinner;

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
        addListeners();
    }

    public MenuButton getSortButton() {
        return sortButton;
    }

    public void addListeners()
    {
        // Add button functionality (just changes the Stage to the NewRecipePageFrame)
        newRecipeButton.setOnAction(e -> {
            // Stage primaryStage = new Stage();
            // //need to pass in recipeList so recipes can be added to it
            // //need to pass in reversedList so recipes can be added to it
            // NewRecipePageFrame NewRecipePage = new NewRecipePageFrame(recipeList, reversedList); 
            // primaryStage.setScene(new Scene(NewRecipePage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            // primaryStage.setResizable(false);
            // primaryStage.show();
            Stage stage = (Stage) newRecipeButton.getScene().getWindow();
            NewRecipePageFrame NewRecipePage = new NewRecipePageFrame(stage, recipeList, reversedList);
            stage.setScene(new Scene(NewRecipePage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();

        });

        logoutButton.setOnAction(e -> {
            try{
                File userFile = new File("user.txt");
                userFile.delete();
            }catch(Exception fileException){
                fileException.printStackTrace();
            }
            

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            LoginPageFrame newLoginPage = new LoginPageFrame();
            stage.setScene(new Scene(newLoginPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();
        });
    

    
        alphaOpt.setOnAction(e -> {

            recipeList = new RecipeListDisplay("storage.json");
            recipeListData = recipeList.getRecipeListData();

            if (filterMenuName != "Filter") {
                recipeListData.filter(filterMenuName);
            }
            recipeListData.sortAlphabetically();

            // Reload Sorted Recipe List from alternate json file
            JSONSaver.saveRecipeListData(recipeListData, "sorted.json");
            Stage stage = (Stage) newRecipeButton.getScene().getWindow();
            RecipeListPageFrame frontPage = new RecipeListPageFrame(alphaOpt.getText(), filterMenuName, "sorted.json");
            stage.setTitle("PantryPal");
            stage.getIcons().add(new Image(Constants.defaultIconPath));
            stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();
        });

        reverseOpt.setOnAction(e -> {
 
            recipeList = new RecipeListDisplay("storage.json");
            recipeListData = recipeList.getRecipeListData();

            if (filterMenuName != "Filter") {
                recipeListData.filter(filterMenuName);
            }
            recipeListData.sortReverseAlphabetically();

            // Reload Sorted Recipe List from alternate json file
            JSONSaver.saveRecipeListData(recipeListData, "sorted.json");
            Stage stage = (Stage) newRecipeButton.getScene().getWindow();
            RecipeListPageFrame frontPage = new RecipeListPageFrame(reverseOpt.getText(), filterMenuName, "sorted.json");
            stage.setTitle("PantryPal");
            stage.getIcons().add(new Image(Constants.defaultIconPath));
            stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();
        });

        newOpt.setOnAction(e -> {

            recipeList = new RecipeListDisplay("storage.json");
            recipeListData = recipeList.getRecipeListData();

            if (filterMenuName != "Filter") {
                recipeListData.filter(filterMenuName);
            }
            recipeListData.sortNewest();

            // Reload Sorted Recipe List from alternate json file
            JSONSaver.saveRecipeListData(recipeListData, "sorted.json");
            Stage stage = (Stage) newRecipeButton.getScene().getWindow();
            RecipeListPageFrame frontPage = new RecipeListPageFrame(newOpt.getText(), filterMenuName, "sorted.json");
            stage.setTitle("PantryPal");
            stage.getIcons().add(new Image(Constants.defaultIconPath));
            stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();
        });

        oldOpt.setOnAction(e -> {

            recipeList = new RecipeListDisplay("storage.json");
            recipeListData = recipeList.getRecipeListData();

            if (filterMenuName != "Filter") {
                recipeListData.filter(filterMenuName);
            }
            recipeListData.sortOldest();

            // Reload Sorted Recipe List from alternate json file
            JSONSaver.saveRecipeListData(recipeListData, "sorted.json");
            Stage stage = (Stage) newRecipeButton.getScene().getWindow();
            RecipeListPageFrame frontPage = new RecipeListPageFrame(oldOpt.getText(), filterMenuName, "sorted.json");
            stage.setTitle("PantryPal");
            stage.getIcons().add(new Image(Constants.defaultIconPath));
            stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();
        });

        all.setOnAction(e -> {

            recipeList = new RecipeListDisplay("storage.json");
            recipeListData = recipeList.getRecipeListData();

            recipeListData.filter("All");
            if (sortMenuName == "A-Z") {
                recipeListData.sortAlphabetically();
            } else if (sortMenuName == "Z-A") {
                recipeListData.sortReverseAlphabetically();
            } else if (sortMenuName == "Newest" || sortMenuName == "Sort") {
                recipeListData.sortNewest();
            } else if (sortMenuName == "Oldest") {
                recipeListData.sortOldest();
            }

            // Reload Filtered Recipe List from alternate json file
            JSONSaver.saveRecipeListData(recipeListData, "filtered.json");
            Stage stage = (Stage) newRecipeButton.getScene().getWindow();
            RecipeListPageFrame frontPage = new RecipeListPageFrame(sortMenuName, all.getText(), "filtered.json");
            stage.setTitle("PantryPal");
            stage.getIcons().add(new Image(Constants.defaultIconPath));
            stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();
        });

        breakfast.setOnAction(e -> {

            recipeList = new RecipeListDisplay("storage.json");
            recipeListData = recipeList.getRecipeListData();

            recipeListData.filter("Breakfast");
            if (sortMenuName == "A-Z") {
                recipeListData.sortAlphabetically();
            } else if (sortMenuName == "Z-A") {
                recipeListData.sortReverseAlphabetically();
            } else if (sortMenuName == "Newest" || sortMenuName == "Sort") {
                recipeListData.sortNewest();
            } else if (sortMenuName == "Oldest") {
                recipeListData.sortOldest();
            }

            // Reload Filtered Recipe List from alternate json file
            JSONSaver.saveRecipeListData(recipeListData, "filtered.json");
            Stage stage = (Stage) newRecipeButton.getScene().getWindow();
            RecipeListPageFrame frontPage = new RecipeListPageFrame(sortMenuName, breakfast.getText(), "filtered.json");
            stage.setTitle("PantryPal");
            stage.getIcons().add(new Image(Constants.defaultIconPath));
            stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();
        });

        lunch.setOnAction(e -> {

            recipeList = new RecipeListDisplay("storage.json");
            recipeListData = recipeList.getRecipeListData();

            recipeListData.filter("Lunch");
            if (sortMenuName == "A-Z") {
                recipeListData.sortAlphabetically();
            } else if (sortMenuName == "Z-A") {
                recipeListData.sortReverseAlphabetically();
            } else if (sortMenuName == "Newest" || sortMenuName == "Sort") {
                recipeListData.sortNewest();
            } else if (sortMenuName == "Oldest") {
                recipeListData.sortOldest();
            }

            // Reload Filtered Recipe List from alternate json file
            JSONSaver.saveRecipeListData(recipeListData, "filtered.json");
            Stage stage = (Stage) newRecipeButton.getScene().getWindow();
            RecipeListPageFrame frontPage = new RecipeListPageFrame(sortMenuName, lunch.getText(), "filtered.json");
            stage.setTitle("PantryPal");
            stage.getIcons().add(new Image(Constants.defaultIconPath));
            stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();
        });

        dinner.setOnAction(e -> {

            recipeList = new RecipeListDisplay("storage.json");
            recipeListData = recipeList.getRecipeListData();

            recipeListData.filter("Dinner");
            if (sortMenuName == "A-Z") {
                recipeListData.sortAlphabetically();
            } else if (sortMenuName == "Z-A") {
                recipeListData.sortReverseAlphabetically();
            } else if (sortMenuName == "Newest" || sortMenuName == "Sort") {
                recipeListData.sortNewest();
            } else if (sortMenuName == "Oldest") {
                recipeListData.sortOldest();
            }

            // Reload Filtered Recipe List from alternate json file
            JSONSaver.saveRecipeListData(recipeListData, "filtered.json");
            Stage stage = (Stage) newRecipeButton.getScene().getWindow();
            RecipeListPageFrame frontPage = new RecipeListPageFrame(sortMenuName, dinner.getText(), "filtered.json");
            stage.setTitle("PantryPal");
            stage.getIcons().add(new Image(Constants.defaultIconPath));
            stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();
        });
    }

    public void setNewRecipeButtonAction(EventHandler<ActionEvent> eventHandler) {
        newRecipeButton.setOnAction(eventHandler);
    }
}