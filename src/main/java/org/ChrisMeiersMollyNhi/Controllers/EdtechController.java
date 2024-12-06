
package org.ChrisMeiersMollyNhi.Controllers;

import com.sun.tools.jconsole.JConsoleContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.ChrisMeiersMollyNhi.PuzzleGenerator;
import org.ChrisMeiersMollyNhi.ApiCall.CallAPI;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EdtechController extends BaseController {

    private ArrayList<String> verticalWordsList;
    private ArrayList<String> horizontalWordsList;
    public void switchToMainScene(ActionEvent event) throws Exception {
        mainScene.switchScene("/MainScene.fxml");
    }

    public void generatePuzzle(ActionEvent event) throws Exception{
        TextArea textArea = (TextArea) mainScene.getRoot().lookup("#textareaWordList");
        //take the text and put it into an arraylist of strings
        ArrayList<String> getInput = new ArrayList<>();
        String[] lines = textArea.getText().split("\n");
        for (String line : lines) {
            getInput.add(line.replaceAll("\\s", ""));
        }

        // Check input formatting
        if (containsDuplicates(getInput)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Duplicate Words Detected");
            alert.setHeaderText("Duplicate Words Detected");
            alert.setContentText("Please remove duplicate words from your input and try again.");
            alert.showAndWait();
        } else {
            boolean retry = true;
            for (int i = 0; i < 10; i++) {
                Collections.shuffle(getInput);
                PuzzleGenerator edTechPuzzle = new PuzzleGenerator(getInput);
                boolean formattingStatus = edTechPuzzle.isValidWordList();
                int[][] crossword = edTechPuzzle.generate();

                this.verticalWordsList = edTechPuzzle.getVerticalWords();
                this.horizontalWordsList = edTechPuzzle.getHorizontalWords();
                boolean wordLengthStatus = this.verticalWordsList.size() + this.horizontalWordsList.size() == getInput.size();


                if (formattingStatus && wordLengthStatus) {
                    buildCrossword(crossword);
                    regenerateHints();
                    retry = false;
                    break;
                }
                System.out.println("try: " + i);
            }
            if (retry){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Word Length");
                alert.setHeaderText("Invalid Word Length");
                alert.setContentText("Unable to build the crossword structure because there are too few words, or the words are incompatible");
                alert.showAndWait();
            }
        }
    }

    /**
     * Event handler which automatically calls regenerateHints
     * @param event
     * @throws Exception
     */
    public void regenerateHints(ActionEvent event) throws Exception{
        regenerateHints();
    }

    private boolean containsDuplicates(ArrayList<String> arr){
        return arr.stream().distinct().count() != arr.size();
    }

    public void regenerateHints() throws Exception{
        if (this.verticalWordsList != null && this.horizontalWordsList != null){

            // Get references to the VBox containers
            VBox vboxLeftContainer = (VBox) mainScene.getRoot().lookup("#leftHints");
            VBox vboxRightContainer = (VBox) mainScene.getRoot().lookup("#rightHints");

            // Clear existing hints
            vboxLeftContainer.getChildren().clear();
            vboxRightContainer.getChildren().clear();

            // CallAPI callApi = new CallAPI();

            // Add new hints
            for (String word : this.verticalWordsList) {
                String hint = CallAPI.generateHint(word);
                Label hintLabel = new Label(extractInitialDigits(word) + " " + hint);
                hintLabel.setWrapText(true); // Enable wrapping
                hintLabel.setMaxWidth(200); // Set a maximum width (adjust as needed)
                vboxLeftContainer.getChildren().add(hintLabel);
            }

            for (String word : this.horizontalWordsList) {
                String hint = CallAPI.generateHint(word);
                Label hintLabel = new Label(extractInitialDigits(word) + " " + hint);
                hintLabel.setWrapText(true); // Enable wrapping
                hintLabel.setMaxWidth(200); // Set a maximum width (adjust as needed)
                vboxRightContainer.getChildren().add(hintLabel);
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Puzzle Generated Yet");
            alert.setHeaderText("No Puzzle Generated Yet");
            alert.setContentText("Please enter a list of words and click \"generate\" before trying to view hints.");
            alert.showAndWait();
        }

    }

    /**
     * Extracts all initial digits from the given word using regex.
     * If no initial digits are found, returns an empty string.
     *
     * @param word The word from which to extract initial digits.
     * @return A string containing the initial digits, or empty if none found.
     */
    private String extractInitialDigits(String word) {
        Pattern INITIAL_DIGITS_PATTERN = Pattern.compile("^\\d+");
        Matcher matcher = INITIAL_DIGITS_PATTERN.matcher(word);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return ""; // or any default value you'd prefer
        }
    }

    /**
     * inside of the element with fx:id crosswordDomain, we need to build our crossword
     * to do this, we will need to create a grid of box elements, which are eather black if the crossword
     * char array element is '\u0000',
     * or white with a text input if not '\u0000'.
     *
     * @param crossword
     */
    private void buildCrossword(int[][] crossword) {
        // Get reference to the GridPane container
        Node node = mainScene.getRoot().lookup("#crosswordDomain");
        javafx.scene.layout.GridPane gridPane = (javafx.scene.layout.GridPane) node;

        // Clear existing children and constraints
        gridPane.getChildren().clear();
        gridPane.getRowConstraints().clear();
        gridPane.getColumnConstraints().clear();

        int numRows = crossword.length;
        int numCols = crossword[0].length;

        // Set dynamic row constraints
        for (int i = 0; i < numRows; i++) {
            javafx.scene.layout.RowConstraints row = new javafx.scene.layout.RowConstraints();
            row.setPercentHeight(100.0 / numRows); // Distribute height evenly across rows
            gridPane.getRowConstraints().add(row);
        }

        // Set dynamic column constraints (optional for symmetry)
        for (int j = 0; j < numCols; j++) {
            javafx.scene.layout.ColumnConstraints column = new javafx.scene.layout.ColumnConstraints();
            column.setPercentWidth(100.0 / numCols); // Distribute width evenly across columns
            gridPane.getColumnConstraints().add(column);
        }

        // Add TextField nodes dynamically
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                javafx.scene.control.TextField textField = new javafx.scene.control.TextField();
                textField.setAlignment(javafx.geometry.Pos.CENTER);

                // Set styles for the TextField
                if (crossword[i][j] == -1) {
                    textField.setStyle("-fx-background-color: #000; -fx-border-color: black;");
                    textField.setEditable(false);
                } else if (crossword[i][j] == 0) {
                    textField.setStyle("-fx-background-color: #ffffff; -fx-border-color: black;");
                    textField.setEditable(false);
                } else {
                    textField.setText(Integer.toString(crossword[i][j]));
                    textField.setStyle("-fx-background-color: #ffffff; -fx-border-color: black;");
                    textField.setEditable(false);
                }

                // Ensure the TextField fills its cell
                GridPane.setHgrow(textField, javafx.scene.layout.Priority.ALWAYS);
                GridPane.setVgrow(textField, javafx.scene.layout.Priority.ALWAYS);

                // Add the TextField to the grid
                gridPane.add(textField, j, i);
            }
        }
    }


    public void printScreen(ActionEvent event) throws Exception{

    }

}