package FrontEnd;

import java.time.LocalDateTime;

import org.json.simple.JSONObject;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class NewRecipePageFrameController implements Controller {

    NewRecipePageFrame view;
    HTTPRequestModel model;

    public NewRecipePageFrameController(NewRecipePageFrame view, HTTPRequestModel model) {
        this.view = view;
        this.model = model;

        this.view.setNewBackButtonAction(this::handleNewBackButton);
        this.view.setNewSaveButtonAction(this::handleNewSaveButton);
        this.view.setNewGenerateButtonAction(this::handleNewGenerateButton);
        this.view.setRecordButtonAction(this::handleRecordButton);
        this.view.setBreakfastButtonAction(this::handleBreakfastButton);
        this.view.setLunchButtonAction(this::handleLunchButton);
        this.view.setDinnerButtonAction(this::handleDinnerButton);
    }

    public void handleNewBackButton(ActionEvent event) {
        RecipeListPageFrame recipeListPage = new RecipeListPageFrame();
        RecipeListPageFrameController recipeListPageController = new RecipeListPageFrameController(recipeListPage,
                model);
        Main.setController(recipeListPageController);
        view.getStage().setTitle("PantryPal");
        view.getStage().getIcons().add(new Image(Constants.defaultIconPath));
        view.getStage().setScene(new Scene(recipeListPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        view.getStage().setResizable(false);
        view.getStage().show();
    }

    public void handleNewSaveButton(ActionEvent event) {

            //add recipe to recipeList
            view.getRecipe().setDateCreated(LocalDateTime.now().toString());
            view.getList().getChildren().add(new RecipeSimple(view.getRecipe()));
            view.getReverse().getChildren().add(0, new RecipeSimple(view.getRecipe()));
            view.getList().sortNewest();
            //save to .json
            JSONSaver.saveRecipeList(view.getList(),"storage.json");
            
            // HTTPRequestModel httpRequestModel = new HTTPRequestModel(); //TODO: Remove when controller is implemented
            String response = model.performRecipeListPOSTRequest();

            if(response.equals("SUCCESS_POST_REQUEST")){
                Alert alert = new Alert(AlertType.INFORMATION, "Recipe Successfully Saved!", ButtonType.OK);
                alert.showAndWait();    
            }
    }

    public void handleNewGenerateButton(ActionEvent event) {
        String name;
        String ingredients;
        String directions;
        String mealTypeString = "Breakfast";
        String date = LocalDateTime.now().toString();

        try {
            mealTypeString = view.getMealTypeString();
        } catch (Exception badMealType) {
            ErrorSys.quickErrorPopup("No Meal Type Selected!\nPlease select a meal type.");
            return;
            // badMealType.printStackTrace();
        }

        String recipeJSONString = model.performCreateRecipeRequest(mealTypeString);

        if (recipeJSONString.equals("EMPTY_RECORDING_ERROR")) {
            ErrorSys.quickErrorPopup("Empty Recording");
            return;
        } else if (recipeJSONString.equals("CHAT_GPT_FAILED_ERROR")) {
            ErrorSys.quickErrorPopup("ChatGPT Failed, please verify ingredients");
            return;
        } else if (recipeJSONString.equals("INVALID_INGREDIENTS_ERROR")) {
            ErrorSys.quickErrorPopup("Ingredients deemed inedible by ChatGPT");
            return;
        }

        JSONObject recipeJSON = JSONSaver.jsonStringToObject(recipeJSONString);
        name = (String) recipeJSON.get("recipeName");
        ingredients = (String) recipeJSON.get("ingredients");
        directions = (String) recipeJSON.get("directions");

        // Recipe recipe = view.getRecipe();
        // recipe = new Recipe(name, ingredients, directions, date, mealTypeString);
        Recipe recipe = new Recipe(name, ingredients, directions, date, mealTypeString);
        view.setRecipe(recipe);
        RecipeContent content = view.getContent();
        content = new RecipeContent(recipe);
        ScrollPane scrollPane = view.getScrollPane();
        scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        // this is not working
        // view.helpSetCenter(content);
        view.setCenter(content);
        Button newGenerateButton = view.getNewGenerateButton();
        newGenerateButton.setText("Re-generate");
        // this.newGenerateButton.setText("Re-generate");
    }

    public void handleRecordButton(ActionEvent event) {
            if(!view.getRecording()){
                view.getRecordButton().setStyle(Constants.recordButtonStyleOn);
                view.getRecordButton().setText("Recording...");
                view.setAudioForm(view.getAudioFormat());
                // audioFormat = getAudioFormat();
                view.startRecording();
                view.setRecording(true);
                // boolean recording = view.getRecording();
                // recording = true;
            }else{
                view.getRecordButton().setStyle(Constants.recordButtonStyleOff);
                view.getRecordButton().setText("Record Ingredients");
                view.stopRecording();
                view.setRecording(false);
                // boolean recording = view.getRecording();
                // recording = false;
            }
    }

    public void handleBreakfastButton(ActionEvent event) {
        view.getBreakfastButton().setStyle(Constants.defaultButtonPressedStyle);
        view.getLunchButton().setStyle(Constants.defaultButtonStyle);
        view.getDinnerButton().setStyle(Constants.defaultButtonStyle);
        view.setMealType(1);
    }

    public void handleLunchButton(ActionEvent event) {
        view.getBreakfastButton().setStyle(Constants.defaultButtonStyle);
        view.getLunchButton().setStyle(Constants.defaultButtonPressedStyle);
        view.getDinnerButton().setStyle(Constants.defaultButtonStyle);
        view.setMealType(2);
    }

    public void handleDinnerButton(ActionEvent event) {
        view.getBreakfastButton().setStyle(Constants.defaultButtonStyle);
        view.getLunchButton().setStyle(Constants.defaultButtonStyle);
        view.getDinnerButton().setStyle(Constants.defaultButtonPressedStyle);
        view.setMealType(3);
    }

}
