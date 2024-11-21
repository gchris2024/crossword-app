package org.ChrisMeiersMollyNhi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ChrisMeiersMollyNhi.Controllers.BaseController;

import java.io.IOException;

public class CrosswordFXMain extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Crossword App");

        switchScene("/MainScene.fxml");
    }

    public void switchScene(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Object controller = loader.getController();
        ((BaseController) controller).setMainScene(this);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
