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

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class CrosswordView {
    private CrosswordModel theModel;

    /** Root container for our stage */
    private BorderPane root;

    /** Temp label */
    private Label lblHello;

    public CrosswordView(CrosswordModel theModel) {
        this.theModel = theModel;

        initSceneGraph();
//        initStyling();
    }

    public BorderPane getRoot() { return root;}

    /**
     * Initialize the entire scene graph
     */
    private void initSceneGraph() {
        this.root = new BorderPane();

        lblHello = new Label("Hello Crossword!!");

        this.root.setCenter(lblHello);
    }
}