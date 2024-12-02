
package org.ChrisMeiersMollyNhi.Controllers;

import javafx.event.ActionEvent;

public class MainSceneController extends BaseController {

    public void switchToEdtech(ActionEvent event) throws Exception {
        mainScene.switchScene("/EdtechScene.fxml");
    }

    public void switchToGame(ActionEvent event) throws Exception {
        mainScene.switchScene("/GameScene.fxml");
    }
}