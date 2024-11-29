
package org.ChrisMeiersMollyNhi.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

public class EdtechController extends BaseController {

    private ArrayList<String> verticalWordsList;
    private ArrayList<String> horizontalWordsList;
    public void switchToMainScene(ActionEvent event) throws Exception {
        mainScene.switchScene("/MainScene.fxml");
    }

    public void generatePuzzle(ActionEvent event) throws Exception{
        boolean formattingStatus = checkFormatting();
        // Check input formatting
        // if (formattingStatus) {
        //      TODO: Implement Molly's Algorithm here!
        //      this.verticalWordsList = getVerticalWordsFromMolly(); // TODO : implement this from Molly's algorithm
        //      this.horizontalWordsList = getHorizontalWordsFromMolly(); // TODO : implement this from Molly's algorithm
        //      regenerateHints();
        // } else {
        //     Alert alert = new Alert(Alert.AlertType.ERROR);
        //     alert.setTitle("Error Dialog");
        //     alert.setHeaderText(null);
        //     alert.setContentText("Please remove any special characters or numbers and try again.");
        //     alert.showAndWait();
        //
    }

    private boolean checkFormatting() {
        TextArea textArea = (TextArea) mainScene.getRoot().lookup("#textareaWordList");
        if (textArea == null) {
            return false;
        }

        String[] lines = textArea.getText().split("\\r?\\n");
        ArrayList<String> wordList = new ArrayList<>();
        for (String line : lines) {
            if (!line.matches("[a-zA-Z]+")) {
                return false;
            }
            wordList.add(line);
        }

        return true;
    }

    public void regenerateHints(ActionEvent event) throws Exception{
        if (this.verticalWordsList != null && this.horizontalWordsList != null){
            /*
        // Get references to the VBox containers
        VBox vboxLeftContainer = (VBox) mainScene.getRoot().lookup("#vboxLeftContainer");
        VBox vboxRightContainer = (VBox) mainScene.getRoot().lookup("#vboxRightContainer");

        // Clear existing hints
        vboxLeftContainer.getChildren().clear();
        vboxRightContainer.getChildren().clear();

        // Add new hints
        for (String word : this.verticalWordsList) {
            String hint = getHintFromNhi(word); TODO: implement this from Nhi's method
            Label hintLabel = new Label(hint);
            vboxLeftContainer.getChildren().add(hintLabel);
        }

        for (String word : this.horizontalWordsList) {
            String hint = getHintFromNhi(word); TODO: implement this from Nhi's method
            Label hintLabel = new Label(hint);
            vboxRightContainer.getChildren().add(hintLabel);
        }
        */
        } else {
            // Throw an error message
            System.out.println("No puzzle generated yet.");
        }

    }

    public void printScreen(ActionEvent event) throws Exception{

    }

}