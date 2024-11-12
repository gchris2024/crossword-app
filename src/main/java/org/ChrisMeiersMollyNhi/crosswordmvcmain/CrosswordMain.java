package org.ChrisMeiersMollyNhi.crosswordmvcmain;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CrosswordMain extends Application {

    private CrosswordModel theModel;
    private CrosswordView theView;
    private CrosswordController theController;

    @Override
    public void init() throws Exception {
        super.init();
        this.theModel = new CrosswordModel();
        this.theView = new CrosswordView(this.theModel);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(theView.getRoot(), 500, 500);

        scene.getStylesheets().add(
                getClass().getResource("/crosswordmvc.css").
                        toExternalForm());

        this.theController = new CrosswordController(this.theModel, this.theView);

        primaryStage.setTitle("Crossword!!");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

    }
}
