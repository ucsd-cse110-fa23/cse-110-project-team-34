package FrontEnd;

import org.json.simple.JSONObject;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;

public class NewRecipePageFrameController implements Controller {

    NewRecipePageFrame view;
    HTTPRequestModel model;

    public NewRecipePageFrameController(NewRecipePageFrame view, HTTPRequestModel model) {
        this.view = view;
        this.model = model;

        this.view.setNewBackButtonAction(this::handleNewBackButton);
        this.view.setNewSaveButtonAction(this::handleNewSaveButton);
        this.view.setNewGenerateButtonAction(null);
        this.view.setRecordButtonAction(this::handleRecordButton);
        this.view.setBreakfastButtonAction(null);
        this.view.setLunchButtonAction(null);
        this.view.setDinnerButtonAction(null);
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

        // add recipe to recipeList
        view.getList().getChildren().add(new RecipeSimple(view.getRecipe()));
        view.getReverse().getChildren().add(0, new RecipeSimple((view.getRecipe())));
        // save to .json
        JSONSaver.saveRecipeList(view.getList());

        String response = model.performRecipeListPOSTRequest();

        // sort tasks, tasks are added at end, just show by reverse order (for loop
        // starting at the end)
    }

    public void handleNewGenerateButton(ActionEvent event) {
        // String name;
        // String ingredients;
        // String directions;
        // String mealTypeString = "Breakfast";
        // try {
        //     mealTypeString = getMealTypeString();
        // } catch (Exception badMealType) {
        //     ErrorSys.quickErrorPopup("No Meal Type Selected!\nPlease select a meal type.");
        //     return;
        //     // badMealType.printStackTrace();
        // }

        // HTTPRequestModel httpRequestModel = new HTTPRequestModel();
        // String recipeJSONString = httpRequestModel.performCreateRecipeRequest(mealTypeString);

        // if (recipeJSONString.equals("EMPTY_RECORDING_ERROR")) {
        //     ErrorSys.quickErrorPopup("Empty Recording");
        //     return;
        // } else if (recipeJSONString.equals("CHAT_GPT_FAILED_ERROR")) {
        //     ErrorSys.quickErrorPopup("ChatGPT Failed, please verify ingredients");
        //     return;
        // } else if (recipeJSONString.equals("INVALID_INGREDIENTS_ERROR")) {
        //     ErrorSys.quickErrorPopup("Ingredients deemed inedible by ChatGPT");
        //     return;
        // }

        // JSONObject recipeJSON = JSONSaver.jsonStringToObject(recipeJSONString);
        // name = (String) recipeJSON.get("recipeName");
        // ingredients = (String) recipeJSON.get("ingredients");
        // directions = (String) recipeJSON.get("directions");

        // recipe = new Recipe(name, ingredients, directions);
        // content = new RecipeContent(recipe);
        // scrollPane = new ScrollPane(content);
        // scrollPane.setFitToWidth(true);
        // scrollPane.setFitToHeight(true);
        // this.setCenter(content);

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

    }

    public void handleLunchButton(ActionEvent event) {

    }

    public void handleDinnerButton(ActionEvent event) {

    }

}
