package FrontEnd;

public class ViewRecipePageFrameController {
    ViewRecipePageFrame view;
    HTTPRequestModel model;

    public ViewRecipePageFrameController(ViewRecipePageFrame view, HTTPRequestModel model) {
        this.view = view;
        this.model = model;

        // this.view.setBackButtonAction(this::handleNewBackButton);
        // this.view.setSaveButtonAction(this::handleNewSaveButton);

    }
}
