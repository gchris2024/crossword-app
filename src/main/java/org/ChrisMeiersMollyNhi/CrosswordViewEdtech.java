/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Fall 2024
 * Instructor: Prof. Lily
 *
 * Name: Chris, Meiers, Molly, Nhi
 *
 * Project: csci205_final_project
 * Package: org.ChrisMeiersMollyNhi
 * Class: CrosswordController
 *
 * Description:
 *
 * ****************************************
 */
package org.ChrisMeiersMollyNhi;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CrosswordViewEdtech {
    private CrosswordModel theModel;

    /** Root container for our stage */
    private BorderPane root;

    /** Upper region of the scene */
    private BorderPane upperRegionContainer;

    public CrosswordViewEdtech(CrosswordModel theModel) {
        this.theModel = theModel;
        initSceneGraph();
    }

    public BorderPane getRoot() { return root;}

    /**
     * Initialize the entire scene graph
     */
    private void initSceneGraph() {
        initializeRoot();
        initializeHintBox();
        initializeUpperRegion();
    }

    private void initializeRoot(){
        this.root = new BorderPane();
        this.root.setId("root");
    }

    private void initializeHintBox() {
            // Create Left Container (Down hints)
        VBox leftContainer = new VBox(10); // Vertical box with spacing for Down header and hints
        BorderPane downHeader = new BorderPane();
        downHeader.setId("hintHeader");
        downHeader.setCenter(new Label("Down"));

        BorderPane downBody = new BorderPane();
        downBody.setId("hintBody");
        downBody.setCenter(new Label(
                "1) hint 1" +
                        "\n2) hint 2" +
                        "\n3) hint 3" +
                        "\n4) hint 4" +
                        "\n5) hint 5"
        ));

        leftContainer.getChildren().addAll(downHeader, downBody);

        // Create Right Container (Across hints)
        VBox rightContainer = new VBox(10); // Vertical box with spacing for Across header and hints
        BorderPane acrossHeader = new BorderPane();
        acrossHeader.setId("hintHeader");
        acrossHeader.setCenter(new Label("Across"));

        BorderPane acrossBody = new BorderPane();
        acrossBody.setId("hintBody");
        acrossBody.setCenter(new Label(
                "1) Across Text" +
                        "\n2) hint 1" +
                        "\n3) hint 2" +
                        "\n4) hint 3" +
                        "\n5) hint 4"
        ));

        rightContainer.getChildren().addAll(acrossHeader, acrossBody);

        // Add Left and Right containers to the main HBox
        HBox mainContainer = new HBox(10); // Horizontal layout with spacing between Left and Right
        mainContainer.getChildren().addAll(leftContainer, rightContainer);
//        mainContainer.setId("itemContainerHintBox");

        //add a scrollpane to put mainContainer into scrolling pane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(mainContainer);
        scrollPane.setId("scrollPaneHintBox");

        // Add the mainContainer to the wordListBox
        this.root.setCenter(scrollPane);
    }

    private void initializeUpperRegion() {
        //Create Upper Region Container
        upperRegionContainer = new BorderPane();
        upperRegionContainer.setId("upperRegion");
        this.root.setTop(upperRegionContainer);

        double percentOfWindow = 0.60;
        upperRegionContainer.setPrefHeight(this.root.getHeight() * percentOfWindow);
        this.root.heightProperty().addListener((obs, oldVal, newVal) -> {
            upperRegionContainer.setPrefHeight(newVal.doubleValue() * percentOfWindow);
        });

        // HintBox and Grid Containers
         BorderPane wordListBox = new BorderPane();
         wordListBox.setId("itemContainerWordBank");

         BorderPane gridContainer = new BorderPane();
         gridContainer.setId("itemContainerGrid");

         dynamicSizingGridWordList(wordListBox, gridContainer);
         initializeGrid(gridContainer);
         initializeWordList(wordListBox);
    }

    private void initializeGrid(BorderPane gridContainer){
        // Create Grid Container
        BorderPane grid = new BorderPane();
    }

    private void initializeWordList(BorderPane wordListBox) {
        BorderPane wordBankContainer = new BorderPane();

        //Wordbank Header
        BorderPane wordBankHeader = new BorderPane();
        wordBankHeader.setId("wordBankHeader");
        Button generateHintsButton = new Button("Generate Hints");
        generateHintsButton.setId("generateHintsButton");
        Label wordBankLabel = new Label("Word Bank:");
        wordBankHeader.setLeft(wordBankLabel);
        wordBankHeader.setRight(generateHintsButton);
        wordBankContainer.setTop(wordBankHeader);

        //Wordbank Input
        TextField wordBankInput = new TextField();
        wordBankContainer.setBottom(wordBankInput);

        double percentOfWindow = 0.8;
        wordBankInput.setPrefHeight(wordListBox.getHeight() * percentOfWindow);
        wordListBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            wordBankInput.setPrefHeight(newVal.doubleValue() * percentOfWindow);
        });

        wordListBox.setBottom(wordBankContainer);
    }

    private void dynamicSizingGridWordList(BorderPane wordListContainer, BorderPane gridContainer) {
        // Set dynamic widths if desired
        double wordListPercent = 0.25;
        double gridPercent = 0.75;
        wordListContainer.setPrefWidth(this.root.getWidth() * wordListPercent);
        gridContainer.setPrefWidth(this.root.getWidth() * gridPercent);

        this.root.widthProperty().addListener((obs, oldVal, newVal) -> {
            wordListContainer.setPrefWidth(newVal.doubleValue() * wordListPercent);
            gridContainer.setPrefWidth(newVal.doubleValue() * gridPercent);
        });

        // Add wordList and Grid to an HBox with spacing
        HBox hBoxContainer = new HBox(10); // 10 pixels of spacing between wordList and Grid
        hBoxContainer.getChildren().addAll(gridContainer, wordListContainer);
        upperRegionContainer.setCenter(hBoxContainer);
    }
}