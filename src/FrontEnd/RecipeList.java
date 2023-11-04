package FrontEnd;

import javafx.scene.layout.*;


//TODO: Create RecipeList functionality
public class RecipeList extends VBox{
    
    RecipeList() {
        this.setSpacing(5); // sets spacing between recipes
        this.setPrefSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.setStyle(Constants.defaultBackgroundColor);
    }

}
