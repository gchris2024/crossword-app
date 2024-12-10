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
 * Class: CrosswordFXMain
 *
 * Description:
 * This class represents the main entry point for the CrosswordFX application.
 * It initializes the primary stage and loads the initial scene from an FXML file.
 * It also provides methods to switch between scenes within the application.

 *
 * ****************************************
 */

package org.ChrisMeiersMollyNhi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ChrisMeiersMollyNhi.Controllers.BaseController;

import java.io.IOException;

/**
 * Main class for the CrosswordFX application.
 * This class extends the JavaFX Application class and serves as the entry point for the application.
 * It initializes the primary stage and sets the initial scene.
 */
public class CrosswordFXMain extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and displays the primary stage for the application.
     * Sets the minimum width and height for the stage and sets the title.
     * Loads and switches to the main scene from the specified FXML file.
     *
     * @param primaryStage The primary stage for this application.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setMinWidth(850); // Match the Min Width set in Scene Builder
        this.primaryStage.setMinHeight(600); // Match the Min Height set in Scene Builder

        primaryStage.setTitle("Crossword App");

        switchScene("/MainScene.fxml");
    }

    /**
     * Switches the current scene to a new scene specified by the given FXML file.
     * Loads the FXML file, sets the controller, and displays the new scene on the primary stage.
     *
     * @param fxmlFile the path to the FXML file to load the new scene from
     * @throws IOException if the FXML file cannot be loaded
     */
    public void switchScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Object controller = loader.getController();
        ((BaseController) controller).setMainScene(this);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Retrieves the root node of the current scene associated with the primary stage.
     *
     * @return the root node of the current scene, or null if no scene is set.
     */
    public Parent getRoot() {
        if (primaryStage.getScene() != null) {
            return primaryStage.getScene().getRoot();
        }
        return null; // In case no scene is set
    }
}
