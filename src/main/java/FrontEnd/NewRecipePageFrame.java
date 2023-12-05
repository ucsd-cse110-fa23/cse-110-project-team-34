package FrontEnd;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javax.sound.sampled.*;

import org.json.simple.JSONObject;

import BackEnd.api.ChatGPT;
import BackEnd.api.Whisper;

import java.io.*;
import java.net.URISyntaxException;

class NewRecipePageHeader extends HBox {
    private Button Breakfast;
    private Button Lunch;
    private Button Dinner;

    NewRecipePageHeader() {

        Breakfast = new Button("Breakfast");
        Lunch = new Button("Lunch");
        Dinner = new Button("Dinner");
        this.setSpacing(15);  

        Breakfast.setStyle(Constants.defaultButtonStyle);
        Lunch.setStyle(Constants.defaultButtonStyle);
        Dinner.setStyle(Constants.defaultButtonStyle);

        this.setPrefSize(Constants.WINDOW_WIDTH, 100); // Size of the header
        this.setStyle(Constants.boldBackgroundColor);


        this.getChildren().addAll(Breakfast, Lunch, Dinner);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }

    public Button getBButton(){
        return Breakfast;
    }

    public Button getLButton(){
        return Lunch;
    }

    public Button getDButton(){
        return Dinner;
    }
}

class NewRecipePageFooter extends HBox {

    private Button BackButton;
    private Button SaveButton;


    NewRecipePageFooter() {
        this.setPrefSize(Constants.WINDOW_WIDTH, 100);
        this.setStyle(Constants.boldBackgroundColor);
        this.setSpacing(15);      

        BackButton = new Button("Back"); // text displayed on add button
        BackButton.setStyle(Constants.defaultButtonStyle); // styling the button

        SaveButton = new Button("Save"); // text displayed on add button
        SaveButton.setStyle(Constants.defaultButtonStyle);

        this.getChildren().addAll(BackButton, SaveButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center

    }

    public Button getBackButton() {
        return BackButton;
    }

    public Button getSaveButton() {
        return SaveButton;
    }

}


class RecipeGenerator extends HBox {
    private Button recordButton;
    private Button generator;

    RecipeGenerator() {
        recordButton = new Button("Record Ingredients");
        recordButton.setStyle(Constants.recordButtonStyleOff);

        generator = new Button("Generate");
        generator.setStyle(Constants.defaultButtonStyle);

        this.getChildren().addAll(recordButton,generator);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(60);
    }
    public Button getGenerator() {
        return generator;
    }

    public Button getRecordButton(){
        return recordButton;
    }
}

class RecipeContent extends VBox {
    private Label recipeNameLabel;
    private Region r1;
    private Label ingredientsLabel;
    private Region r2;
    private Label directionsLabel;

    RecipeContent(Recipe recipe) {
        int width = 500;
        recipeNameLabel = new Label("Recipe Name: " + recipe.getRecipeName());
        recipeNameLabel.setMaxWidth(width);
        recipeNameLabel.setWrapText(true);
        recipeNameLabel.setStyle(Constants.defaultTextStyle);

        r1 = new Region();
        r1.setPrefSize(width, 50);

        ingredientsLabel = new Label("Ingredients: " + recipe.getIngredients());
        ingredientsLabel.setMaxWidth(width);
        ingredientsLabel.setWrapText(true);
        ingredientsLabel.setStyle(Constants.defaultTextStyle);

        r2 =  new Region();
        r2.setPrefSize(width, 50);

        directionsLabel = new Label("Directions: " + recipe.getDirections());
        directionsLabel.setMaxWidth(width);
        directionsLabel.setWrapText(true);
        directionsLabel.setStyle(Constants.defaultTextStyle);

        this.getChildren().addAll(recipeNameLabel, r1, ingredientsLabel, r2, directionsLabel);
        this.setAlignment(Pos.CENTER);
    }
}

public class NewRecipePageFrame extends BorderPane{

    /**
     * Declare Scene Elements Here
     */
    private NewRecipePageHeader header;
    private NewRecipePageFooter footer;
    private ScrollPane scrollPane;
    private RecipeGenerator generator;
    private Recipe recipe;
    private RecipeContent content;
    private RecipeList list;
    private RecipeList reverse;
    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;

    private Button breakfastButton;
    private Button lunchButton;
    private Button dinnerButton;
    
    /**
     * Page properties
     */
    private boolean recording = false;
    private int mealType = 0; //1 == breakfast, 2 == lunch, 3 == dinner, else undef/null
    private Stage stage;

    public void setMealType(int num) {
        mealType = num;
    }
    public void setAudioForm(AudioFormat audioForm){
        audioFormat = audioForm;
    }

    public void setRecording(boolean TF) {
        recording = TF;
    }

    public boolean getRecording() {
        return recording;
    }

    public Stage getStage() {
        return stage;
    }

    public RecipeList getList() {
        return list;
    }

    public RecipeList getReverse() {
        return reverse;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public RecipeContent getContent() {
        return content;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    /**
     * Declare Scene Buttons Here
     */
    Button newSaveButton;
    Button newBackButton;
    Button newGenerateButton;
    Button recordButton;

    public Button getNewSaveButton() {
        return newSaveButton;
    }

    public Button getNewBackButton() {
        return newBackButton;
    }

    public Button getNewGenerateButton() {
        return newGenerateButton;
    }

    public Button getRecordButton() {
        return recordButton;
    }

    public Button getBreakfastButton() {
        return breakfastButton;
    }

    public Button getLunchButton() {
        return lunchButton;
    }

    public Button getDinnerButton() {
        return dinnerButton;
    }

    // public NewRecipePageFrame newRep() {
    //     return this;
    // }

    public void helpSetCenter(RecipeContent content) {
        this.content = content;
    }

    NewRecipePageFrame(Stage stage, RecipeList recipeList, RecipeList reverseList)
    {
        /**
         * Initialize / Assign Elements Here
         */

        this.stage = stage;
        recipe = new Recipe("Sample Recipe", "Sample Ingredients", "Sample Directions");
        header = new NewRecipePageHeader();
        footer = new NewRecipePageFooter();
        generator = new RecipeGenerator();
        content = new RecipeContent(recipe);
        list = recipeList;
        reverse = reverseList;

        scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        breakfastButton = header.getBButton();
        lunchButton = header.getLButton();
        dinnerButton = header.getDButton();

        newBackButton = footer.getBackButton();
        newSaveButton = footer.getSaveButton();
        newGenerateButton = generator.getGenerator();
        recordButton = generator.getRecordButton();

        VBox vBox = new VBox();
        vBox.getChildren().addAll(header, generator);

        /**
         * Set element positions here
         */
        this.setTop(vBox);
        this.setCenter(content);
        this.setBottom(footer);


        //Add button listeners
        //addListeners();
    }

    public void setNewBackButtonAction(EventHandler<ActionEvent> eventHandler) {
        newBackButton.setOnAction(eventHandler);
    }

    public void setNewSaveButtonAction(EventHandler<ActionEvent> eventHandler) {
        newSaveButton.setOnAction(eventHandler);
    }
    
    public void setNewGenerateButtonAction(EventHandler<ActionEvent> eventHandler) {
        newGenerateButton.setOnAction(eventHandler);
    }
    
    
    public void setRecordButtonAction(EventHandler<ActionEvent> eventHandler) {
        recordButton.setOnAction(eventHandler);
    }

    public void setBreakfastButtonAction(EventHandler<ActionEvent> eventHandler) {
        breakfastButton.setOnAction(eventHandler);
    }

    public void setLunchButtonAction(EventHandler<ActionEvent> eventHandler) {
        lunchButton.setOnAction(eventHandler);
    }

    public void setDinnerButtonAction(EventHandler<ActionEvent> eventHandler) {
        dinnerButton.setOnAction(eventHandler);
    }
  
    public void addListeners()
    {

        // Add button functionality
        newBackButton.setOnAction(e -> {
            RecipeListPageFrame frontPage = new RecipeListPageFrame();
            stage.setTitle("PantryPal");
            stage.getIcons().add(new Image(Constants.defaultIconPath));
            stage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            stage.setResizable(false);
            stage.show();
            
        });

        newSaveButton.setOnAction(e -> {
            //add recipe to recipeList
            list.getChildren().add(new RecipeSimple(recipe));
            reverse.getChildren().add(0, new RecipeSimple(recipe));
            //save to .json
            JSONSaver.saveRecipeList(list);
            
            HTTPRequestModel httpRequestModel = new HTTPRequestModel(); //TODO: Remove when controller is implemented
            String response = httpRequestModel.performRecipeListPOSTRequest();

            //sort tasks, tasks are added at end, just show by reverse order (for loop starting at the end)
        });

        newGenerateButton.setOnAction(e -> {
            String name;
            String ingredients;
            String directions;
            String mealTypeString = "Breakfast";
            try{
                mealTypeString = getMealTypeString();
            }catch(Exception badMealType){
                ErrorSys.quickErrorPopup("No Meal Type Selected!\nPlease select a meal type.");
                return;
                //badMealType.printStackTrace();
            }

            HTTPRequestModel httpRequestModel = new HTTPRequestModel();
            String recipeJSONString = httpRequestModel.performCreateRecipeRequest(mealTypeString);

            if(recipeJSONString.equals("EMPTY_RECORDING_ERROR")){
                ErrorSys.quickErrorPopup("Empty Recording");
                return;
            }else if(recipeJSONString.equals("CHAT_GPT_FAILED_ERROR")){
                ErrorSys.quickErrorPopup("ChatGPT Failed, please verify ingredients");
                return;
            }else if(recipeJSONString.equals("INVALID_INGREDIENTS_ERROR")){
                ErrorSys.quickErrorPopup("Ingredients deemed inedible by ChatGPT");
                return;
            }

            JSONObject recipeJSON = JSONSaver.jsonStringToObject(recipeJSONString);
            name = (String)recipeJSON.get("recipeName");
            ingredients = (String)recipeJSON.get("ingredients");
            directions = (String)recipeJSON.get("directions");

            recipe = new Recipe(name, ingredients, directions);
            content = new RecipeContent(recipe);
            scrollPane = new ScrollPane(content);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            this.setCenter(content);

            this.newGenerateButton.setText("Re-generate");

        });

        recordButton.setOnAction(e -> {

            // 1) record voice
            if(!recording){
                recordButton.setStyle(Constants.recordButtonStyleOn);
                recordButton.setText("Recording...");
                audioFormat = getAudioFormat();
                startRecording();
                recording = true;
            }else{
                recordButton.setStyle(Constants.recordButtonStyleOff);
                recordButton.setText("Record Ingredients");
                stopRecording();
                recording = false;
            }
            // 2) plug into chatGPT
            // 3) get output and set to name, ingredients, directions


            // recipe.setRecipeName(name);
            // recipe.setIngredients(ingredients);
            // recipe.setDirections(directions);
        });

        breakfastButton.setOnAction(e -> {
            breakfastButton.setStyle(Constants.defaultButtonPressedStyle);
            lunchButton.setStyle(Constants.defaultButtonStyle);
            dinnerButton.setStyle(Constants.defaultButtonStyle);
            mealType = 1;
        });

        lunchButton.setOnAction(e -> {
            lunchButton.setStyle(Constants.defaultButtonPressedStyle);
            breakfastButton.setStyle(Constants.defaultButtonStyle);
            dinnerButton.setStyle(Constants.defaultButtonStyle);
            mealType = 2;
        });

        dinnerButton.setOnAction(e -> {
            dinnerButton.setStyle(Constants.defaultButtonPressedStyle);
            breakfastButton.setStyle(Constants.defaultButtonStyle);
            lunchButton.setStyle(Constants.defaultButtonStyle);
            mealType = 3;
        });
        
    }

    public AudioFormat getAudioFormat() {
        // the number of samples of audio per second.
        // 44100 represents the typical sample rate for CD-quality audio.
        float sampleRate = 44100;

        // the number of bits in each sample of a sound that has been digitized.
        int sampleSizeInBits = 16;

        // the number of audio channels in this format (1 for mono, 2 for stereo).
        int channels = 2;

        // whether the data is signed or unsigned.
        boolean signed = true;

        // whether the audio data is stored in big-endian or little-endian order.
        boolean bigEndian = false;

        return new AudioFormat(
            sampleRate,
            sampleSizeInBits,
            channels,
            signed,
            bigEndian);
    }

    public void startRecording() {
        Thread t = new Thread(
            new Runnable() {
                @Override
                public void run(){
                    try {
                        // the format of the TargetDataLine
                        DataLine.Info dataLineInfo = new DataLine.Info(
                                TargetDataLine.class,
                                audioFormat);
                        // the TargetDataLine used to capture audio data from the microphone
                        targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
                        targetDataLine.open(audioFormat);
                        targetDataLine.start();
                        // recordingLabel.setVisible(true);

                        // the AudioInputStream that will be used to write the audio data to a file
                        AudioInputStream audioInputStream = new AudioInputStream(
                                targetDataLine);

                        // the file that will contain the audio data
                        File audioFile = new File("CSE110Voice.wav");
                        AudioSystem.write(
                                audioInputStream,
                                AudioFileFormat.Type.WAVE,
                                audioFile);
                        // recordingLabel.setVisible(false);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            });
        t.start();
    }

    public void stopRecording() {
        targetDataLine.stop();
        targetDataLine.close();
    }

    public int getMealType(){
        return mealType;
    }

    public String getMealTypeString() throws Exception{
        if(mealType == 1){
            return "Breakfast";
        }else if(mealType == 2){
            return "Lunch";
        }else if(mealType == 3){
            return "Dinner";
        }else{
            throw new Exception("Invalid meal type, pleas select meal type");
        }
    }
}