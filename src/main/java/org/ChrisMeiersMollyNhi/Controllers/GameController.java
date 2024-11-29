
package org.ChrisMeiersMollyNhi.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class GameController extends BaseController {
    public void switchToMainScene(ActionEvent event) throws Exception {
        mainScene.switchScene("/MainScene.fxml");
    }

    public void generateCrossword(ActionEvent event) throws Exception {
        /*
        ArrayList<String> wordList = generateListOfWords();

        // TODO: Implement Molly's Algorithm here!
        // verticalWordsList = getVerticalWordsFromMolly(); // TODO : implement this from Molly's algorithm
        // horizontalWordsList = getHorizontalWordsFromMolly(); // TODO : implement this from Molly's algorithm

        //generateHints(verticalWordsList, horizontalWordsList);
        */
    }

    private void generateHints(
            ArrayList<String> verticalWordsList,
            ArrayList<String> horizontalWordsList) {
        /*
        // Get references to the VBox containers
        VBox vboxLeftContainer = (VBox) mainScene.getRoot().lookup("#vboxLeftContainer");
        VBox vboxRightContainer = (VBox) mainScene.getRoot().lookup("#vboxRightContainer");

        // Clear existing hints
        vboxLeftContainer.getChildren().clear();
        vboxRightContainer.getChildren().clear();

        // Add new hints
        for (String word : verticalWordsList) {
            String hint = getHintFromNhi(word); TODO: implement this from Nhi's method
            Label hintLabel = new Label(hint);
            vboxLeftContainer.getChildren().add(hintLabel);
        }

        for (String word : horizontalWordsList) {
            String hint = getHintFromNhi(word); TODO: implement this from Nhi's method
            Label hintLabel = new Label(hint);
            vboxRightContainer.getChildren().add(hintLabel);
        }
        */
    }

    public void checkCrossword(ActionEvent event) throws Exception{
        // TODO: Check if user has completed crossword correctly... this will be hard
    }


}