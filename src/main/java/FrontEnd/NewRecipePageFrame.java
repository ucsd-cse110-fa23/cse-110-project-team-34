package FrontEnd;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javax.sound.sampled.*;

import org.json.simple.JSONObject;

import BackEnd.api.ChatGPT;
import BackEnd.api.Whisper;

import java.io.*;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

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
    private Image image;
    private ImageView imageView;

    RecipeContent(Recipe recipe) {
        int width = 500;

        recipeNameLabel = new Label("Recipe Name: " + recipe.getRecipeName());
        recipeNameLabel.setMaxWidth(width);
        recipeNameLabel.setWrapText(true);
        recipeNameLabel.setStyle(Constants.defaultTextStyle);

        image = new Image(recipe.getImg());
        imageView = new ImageView(image);
        // imageView.setPreserveRatio(true);
        imageView.setFitWidth(400);
        imageView.setFitHeight(275);

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

        this.getChildren().addAll(imageView, recipeNameLabel, r1, ingredientsLabel, r2, directionsLabel);
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

    public void setRecipe(Recipe rec) {
        recipe = rec;
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


    public void helpSetCenter(RecipeContent content) {
        this.content = content;
    }

    NewRecipePageFrame(Stage stage, RecipeList recipeList, RecipeList reverseList)
    {
        /**
         * Initialize / Assign Elements Here
         */

        this.stage = stage;
        recipe = new Recipe("Sample Recipe", "Sample Ingredients", "Sample Directions", "Sample Date & Time", "Sample Meal Type");
        recipe.setImg("https://img.freepik.com/premium-photo/cat-wearing-chef-hat-sits-counter-front-stove-with-cooking-utensils_256339-3088.jpg"); //placeholder picture
        header = new NewRecipePageHeader();
        footer = new NewRecipePageFooter();
        generator = new RecipeGenerator();
        content = new RecipeContent(recipe);
        list = recipeList;
        reverse = reverseList;

        scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        // scrollPane.setFitToHeight(true);
        
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
        this.setCenter(scrollPane);
        this.setBottom(footer);

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