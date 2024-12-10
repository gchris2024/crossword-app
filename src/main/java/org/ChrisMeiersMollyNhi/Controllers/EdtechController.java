/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Fall 2024
 * Instructor: Prof. Lily
 *
 * Name: Molly Yoder
 * Section: 10am
 * Date: 12/9/2024
 * Time: 1:09 PM
 *
 * Project: csci205_final_project
 * Package: org.ChrisMeiersMollyNhi
 * Class: EdtechController
 *
 * Description: EdtechController handles the logic for generating puzzles and displaying them in the GUI.
 * It also manages the display of hints for both vertical and horizontal words within the crossword puzzle.
 *
 * ****************************************
 */
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

    /**
     * Generates a crossword puzzle based on the input from a TextArea.
     * The method reads words from the TextArea, checks for duplicates, and attempts to generate a valid puzzle.
     * If duplicates are found, an error alert is shown. If the puzzle cannot be generated after 10 attempts,
     * an error alert is shown indicating invalid word length or incompatibility.
     *
     * @param event The ActionEvent triggered by the user interaction.
     * @throws Exception if an error occurs during puzzle generation.
     */
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

    /**
     * Checks if the given ArrayList contains any duplicate elements.
     *
     * @param arr the ArrayList of Strings to be checked for duplicates
     * @return true if duplicates are found, false otherwise
     */
    private boolean containsDuplicates(ArrayList<String> arr){
        return arr.stream().distinct().count() != arr.size();
    }

    /**
     * Regenerates the hints for the vertical and horizontal word lists.
     * <p>
     * This method clears existing hints from the left and right VBox containers
     * and generates new hints for each word in the vertical and horizontal word lists.
     * The hints are displayed as labels with wrapped text and a maximum width.
     * If the word lists are not initialized, an error alert is shown.
     *
     * @throws Exception if an error occurs during hint generation.
     */
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
                // Create a label
                javafx.scene.control.Label label = new javafx.scene.control.Label();
                label.setAlignment(javafx.geometry.Pos.CENTER);

                // Create a pane to contain the label
                javafx.scene.layout.Pane pane = new javafx.scene.layout.Pane();
                pane.getChildren().add(label);


                // Set styles for the pane and update the label text
                if (crossword[i][j] == -1) {
                    pane.setStyle("-fx-border-color: transparent;");
                } else if (crossword[i][j] == 0) {
                    pane.setStyle("-fx-border-color: black;");
                } else {
                    label.setText(Integer.toString(crossword[i][j])); // Set text on the label
                    pane.setStyle("-fx-border-color: black;");
                    // Manually position the label with padding
                    double padding = 1.0; // 1px padding
                    label.setLayoutX(padding); // Move the label right by 1px
                    label.setLayoutY(padding); // Move the label down by 1px

                    pane.setPrefWidth(50 + 2 * padding); // Adjust to include padding
                    pane.setPrefHeight(50 - 2 * padding); // Adjust to include padding
                }

                // Ensure the pane fills its cell
                GridPane.setHgrow(pane, javafx.scene.layout.Priority.ALWAYS);
                GridPane.setVgrow(pane, javafx.scene.layout.Priority.ALWAYS);

                // Add the pane to the grid
                gridPane.add(pane, j, i);
            }
        }

    }
}