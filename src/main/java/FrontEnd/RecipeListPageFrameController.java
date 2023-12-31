package FrontEnd;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        this.view.setAlphaOptAction(this::handleAlphaOpt);
        this.view.setReverseOptAction(this::handleReverseOpt);
        this.view.setNewOptAction(this::handleNewOpt);
        this.view.setOldOptAction(this::handleOldOpt);
        this.view.setAllAction(this::handleAll);
        this.view.setBreakfastAction(this::handleBreakfast);
        this.view.setLunchAction(this::handleLunch);
        this.view.setDinnerAction(this::handleDinner);

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

    private void handleAlphaOpt(ActionEvent event) {
        RecipeListDisplay recipeList = view.getRecipeList();
        recipeList = new RecipeListDisplay("storage.json");
        RecipeListData recipeListData = recipeList.getRecipeListData();

        if (view.getFilterMenuName() != "Filter" && view.getFilterMenuName() != "All") {
            recipeListData.filter(view.getFilterMenuName());
        }
        recipeListData.sortAlphabetically();

        // Reload Sorted Recipe List from alternate json file
        JSONSaver.saveRecipeListData(recipeListData, "sorted.json");
        Stage stage = (Stage) view.getNewRecipeButton().getScene().getWindow();
        RecipeListPageFrame frontPage = new RecipeListPageFrame(view.getAlphaOpt().getText(), view.getFilterMenuName(),
                "sorted.json");
        RecipeListPageFrameController controller = new RecipeListPageFrameController(frontPage, model);
        Main.setController(controller);
        stage.setTitle("PantryPal");
        stage.getIcons().add(new Image(Constants.defaultIconPath));
        stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        stage.setResizable(false);
        stage.show();
    }

    private void handleReverseOpt(ActionEvent event) {
        RecipeListDisplay recipeList = view.getRecipeList();
        recipeList = new RecipeListDisplay("storage.json");
        RecipeListData recipeListData = recipeList.getRecipeListData();

        if (view.getFilterMenuName() != "Filter" && view.getFilterMenuName() != "All") {
            recipeListData.filter(view.getFilterMenuName());
        }
        recipeListData.sortReverseAlphabetically();

        // Reload Sorted Recipe List from alternate json file
        JSONSaver.saveRecipeListData(recipeListData, "sorted.json");
        Stage stage = (Stage) view.getNewRecipeButton().getScene().getWindow();
        RecipeListPageFrame frontPage = new RecipeListPageFrame(view.getReverseOpt().getText(),
                view.getFilterMenuName(), "sorted.json");
        RecipeListPageFrameController controller = new RecipeListPageFrameController(frontPage, model);
        Main.setController(controller);
        stage.setTitle("PantryPal");
        stage.getIcons().add(new Image(Constants.defaultIconPath));
        stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        stage.setResizable(false);
        stage.show();
    }

    private void handleNewOpt(ActionEvent event) {
        RecipeListDisplay recipeList = view.getRecipeList();
        recipeList = new RecipeListDisplay("storage.json");
        RecipeListData recipeListData = recipeList.getRecipeListData();

        if (view.getFilterMenuName() != "Filter" && view.getFilterMenuName() != "All") {
            recipeListData.filter(view.getFilterMenuName());
        }
        recipeListData.sortNewest();

        // Reload Sorted Recipe List from alternate json file
        JSONSaver.saveRecipeListData(recipeListData, "sorted.json");
        Stage stage = (Stage) view.getNewRecipeButton().getScene().getWindow();
        RecipeListPageFrame frontPage = new RecipeListPageFrame(view.getNewOpt().getText(), view.getFilterMenuName(),
                "sorted.json");
        RecipeListPageFrameController controller = new RecipeListPageFrameController(frontPage, model);
        Main.setController(controller);
        stage.setTitle("PantryPal");
        stage.getIcons().add(new Image(Constants.defaultIconPath));
        stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        stage.setResizable(false);
        stage.show();
    }

    private void handleOldOpt(ActionEvent event) {
        RecipeListDisplay recipeList = view.getRecipeList();
        recipeList = new RecipeListDisplay("storage.json");
        RecipeListData recipeListData = recipeList.getRecipeListData();

        if (view.getFilterMenuName() != "Filter" && view.getFilterMenuName() != "All") {
            recipeListData.filter(view.getFilterMenuName());
        }
        recipeListData.sortOldest();

        // Reload Sorted Recipe List from alternate json file
        JSONSaver.saveRecipeListData(recipeListData, "sorted.json");
        Stage stage = (Stage) view.getNewRecipeButton().getScene().getWindow();
        RecipeListPageFrame frontPage = new RecipeListPageFrame(view.getOldOpt().getText(), view.getFilterMenuName(),
                "sorted.json");
        RecipeListPageFrameController controller = new RecipeListPageFrameController(frontPage, model);
        Main.setController(controller);
        stage.setTitle("PantryPal");
        stage.getIcons().add(new Image(Constants.defaultIconPath));
        stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        stage.setResizable(false);
        stage.show();
    }

    private void handleAll(ActionEvent event) {
        RecipeListDisplay recipeList = view.getRecipeList();
        recipeList = new RecipeListDisplay("storage.json");
        RecipeListData recipeListData = recipeList.getRecipeListData();

        if (view.getSortMenuName() == "A-Z") {
            recipeListData.sortAlphabetically();
        } else if (view.getSortMenuName() == "Z-A") {
            recipeListData.sortReverseAlphabetically();
        } else if (view.getSortMenuName() == "Newest" || view.getSortMenuName() == "Sort") {
            recipeListData.sortNewest();
        } else if (view.getSortMenuName() == "Oldest") {
            recipeListData.sortOldest();
        }

        // Reload Filtered Recipe List from alternate json file
        JSONSaver.saveRecipeListData(recipeListData, "filtered.json");
        Stage stage = (Stage) view.getNewRecipeButton().getScene().getWindow();
        RecipeListPageFrame frontPage = new RecipeListPageFrame(view.getSortMenuName(), view.getAll().getText(),
                "filtered.json");
        RecipeListPageFrameController controller = new RecipeListPageFrameController(frontPage, model);
        Main.setController(controller);
        stage.setTitle("PantryPal");
        stage.getIcons().add(new Image(Constants.defaultIconPath));
        stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        stage.setResizable(false);
        stage.show();
    }

    private void handleBreakfast(ActionEvent event) {
        RecipeListDisplay recipeList = view.getRecipeList();
        recipeList = new RecipeListDisplay("storage.json");
        RecipeListData recipeListData = recipeList.getRecipeListData();

        recipeListData.filter("Breakfast");
        if (view.getSortMenuName() == "A-Z") {
            recipeListData.sortAlphabetically();
        } else if (view.getSortMenuName() == "Z-A") {
            recipeListData.sortReverseAlphabetically();
        } else if (view.getSortMenuName() == "Newest" || view.getSortMenuName() == "Sort") {
            recipeListData.sortNewest();
        } else if (view.getSortMenuName() == "Oldest") {
            recipeListData.sortOldest();
        }

        // Reload Filtered Recipe List from alternate json file
        JSONSaver.saveRecipeListData(recipeListData, "filtered.json");
        Stage stage = (Stage) view.getNewRecipeButton().getScene().getWindow();
        RecipeListPageFrame frontPage = new RecipeListPageFrame(view.getSortMenuName(), view.getBreakfast().getText(),
                "filtered.json");
        RecipeListPageFrameController controller = new RecipeListPageFrameController(frontPage, model);
        Main.setController(controller);
        stage.setTitle("PantryPal");
        stage.getIcons().add(new Image(Constants.defaultIconPath));
        stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        stage.setResizable(false);
        stage.show();
    }

    private void handleLunch(ActionEvent event) {
        RecipeListDisplay recipeList = view.getRecipeList();
        recipeList = new RecipeListDisplay("storage.json");
        RecipeListData recipeListData = recipeList.getRecipeListData();

        recipeListData.filter("Lunch");
        if (view.getSortMenuName() == "A-Z") {
            recipeListData.sortAlphabetically();
        } else if (view.getSortMenuName() == "Z-A") {
            recipeListData.sortReverseAlphabetically();
        } else if (view.getSortMenuName() == "Newest" || view.getSortMenuName() == "Sort") {
            recipeListData.sortNewest();
        } else if (view.getSortMenuName() == "Oldest") {
            recipeListData.sortOldest();
        }

        // Reload Filtered Recipe List from alternate json file
        JSONSaver.saveRecipeListData(recipeListData, "filtered.json");
        Stage stage = (Stage) view.getNewRecipeButton().getScene().getWindow();
        RecipeListPageFrame frontPage = new RecipeListPageFrame(view.getSortMenuName(), view.getLunch().getText(),
                "filtered.json");
        RecipeListPageFrameController controller = new RecipeListPageFrameController(frontPage, model);
        Main.setController(controller);
        stage.setTitle("PantryPal");
        stage.getIcons().add(new Image(Constants.defaultIconPath));
        stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        stage.setResizable(false);
        stage.show();
    }

    private void handleDinner(ActionEvent event) {
        RecipeListDisplay recipeList = view.getRecipeList();
        recipeList = new RecipeListDisplay("storage.json");
        RecipeListData recipeListData = recipeList.getRecipeListData();

        recipeListData.filter("Dinner");
        if (view.getSortMenuName() == "A-Z") {
            recipeListData.sortAlphabetically();
        } else if (view.getSortMenuName() == "Z-A") {
            recipeListData.sortReverseAlphabetically();
        } else if (view.getSortMenuName() == "Newest" || view.getSortMenuName() == "Sort") {
            recipeListData.sortNewest();
        } else if (view.getSortMenuName() == "Oldest") {
            recipeListData.sortOldest();
        }

        // Reload Filtered Recipe List from alternate json file
        JSONSaver.saveRecipeListData(recipeListData, "filtered.json");
        Stage stage = (Stage) view.getNewRecipeButton().getScene().getWindow();
        RecipeListPageFrame frontPage = new RecipeListPageFrame(view.getSortMenuName(), view.getDinner().getText(),
                "filtered.json");
        RecipeListPageFrameController controller = new RecipeListPageFrameController(frontPage, model);
        Main.setController(controller);
        stage.setTitle("PantryPal");
        stage.getIcons().add(new Image(Constants.defaultIconPath));
        stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        stage.setResizable(false);
        stage.show();
    }

}
