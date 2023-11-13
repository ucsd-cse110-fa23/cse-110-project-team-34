package FrontEnd;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javax.sound.sampled.*;
import java.io.*;

class FrontPageHeader extends HBox {

    FrontPageHeader() {
        this.setPrefSize(Constants.WINDOW_WIDTH, 100); // Size of the header
        this.setStyle(Constants.boldBackgroundColor);

        Text titleText = new Text("PantryPal"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 80;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }
}

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
}

class FrontPageFooter extends HBox {

    private Button newRecipeButton;

    FrontPageFooter() {
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
    private Label ingredientsLabel;
    private Label directionsLabel;

    RecipeContent(Recipe recipe) {
        recipeNameLabel = new Label("Recipe Name: " + recipe.getRecipeName());
        ingredientsLabel = new Label("Ingredients: " + recipe.getIngredients());
        directionsLabel = new Label("Directions: " + recipe.getDirections());
        this.getChildren().addAll(recipeNameLabel, ingredientsLabel, directionsLabel);
        this.setAlignment(Pos.CENTER);
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


class FrontPageFrame extends BorderPane{

    /**
     * Declare Scene Elements Here
     */
    private FrontPageHeader header;
    private FrontPageFooter footer;
    private VBox recipeListComplete;
    private ScrollPane recipeListScrollPane;
    private Label recipeListLabel;
    private RecipeList recipeList;
    private RecipeList reversedList;


    /**
     * Declare Scene Buttons Here
     */
    Button newRecipeButton;


    FrontPageFrame()
    {
        /**
         * Initialize / Assign Elements Here
         */
        header = new FrontPageHeader();
        footer = new FrontPageFooter();
        recipeList = new RecipeList(); //default constructor reads .json file
        recipeListComplete = new VBox();

        recipeListLabel = new Label("Recipe List:");
        recipeListLabel.setStyle(Constants.defaultTextStyle);
        recipeListLabel.setPadding(new Insets(10));
        reversedList = new RecipeList(recipeList); //Uses new RecipeList constructor to reverse the order

        recipeListScrollPane = new ScrollPane(reversedList);
        recipeListScrollPane.setFitToWidth(true);
        recipeListScrollPane.setFitToHeight(true);

        recipeListComplete.setStyle(Constants.defaultBackgroundColor);
        recipeListComplete.getChildren().addAll(recipeListLabel, recipeListScrollPane);
        
        newRecipeButton = footer.getNewRecipeButton();

        /**
         * Set element positions here
         */
        this.setTop(header);
        this.setCenter(recipeListComplete);
        this.setBottom(footer);


        //Add button listeners
        addListeners();
    }

    public void addListeners()
    {
        // Add button functionality (just changes the Stage to the NewRecipePageFrame)
        newRecipeButton.setOnAction(e -> {
            Stage newRecipeWindow = new Stage();
            newRecipeWindow.setTitle("Add New Recipe");
            newRecipeWindow.getIcons().add(new Image(Constants.addIconPath));
            //need to pass in recipeList so recipes can be added to it
            //need to pass in reversedList so recipes can be added to it
            NewRecipePageFrame NewRecipePage = new NewRecipePageFrame(recipeList, reversedList); 
            newRecipeWindow.setScene(new Scene(NewRecipePage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
            newRecipeWindow.setResizable(false);
            newRecipeWindow.show();
        });
    }
}


class NewRecipePageFrame extends BorderPane{

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
    private boolean recording = false;

    /**
     * Declare Scene Buttons Here
     */
    Button newSaveButton;
    Button newBackButton;
    Button newGenerateButton;
    Button recordButton;


    NewRecipePageFrame(RecipeList recipeList, RecipeList reverseList)
    {
        /**
         * Initialize / Assign Elements Here
         */

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
        addListeners();
    }

    public void addListeners()
    {

        // Add button functionality
        newBackButton.setOnAction(e -> {
            if(recording){
                stopRecording();
            }
            Stage newRecipeWindow = (Stage) newBackButton.getScene().getWindow();
            newRecipeWindow.close();
            
        });

        newSaveButton.setOnAction(e -> {
            //add recipe to recipeList
            list.getChildren().add(new RecipeSimple(recipe));
            reverse.getChildren().add(0, new RecipeSimple(recipe));
            //save to .json
            //sort tasks, tasks are added at end, just show by reverse order (for loop starting at the end)
        });

        newGenerateButton.setOnAction(e -> {

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
        
    }

    private AudioFormat getAudioFormat() {
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

    private void startRecording() {
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
                        File audioFile = new File("recording.wav");
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

    private void stopRecording() {
        targetDataLine.stop();
        targetDataLine.close();
    }
}

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        //FrontPage layout
        FrontPageFrame frontPage = new FrontPageFrame();
        
        // Set the title of the app
        primaryStage.setTitle("PantryPal");
        // Set the window icon
        primaryStage.getIcons().add(new Image(Constants.defaultIconPath));


        // Create scene of mentioned size with the border pane
        primaryStage.setScene(new Scene(frontPage, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));

        // Make window non-resizable
        primaryStage.setResizable(false);
        // Show the app
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}