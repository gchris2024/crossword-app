
package org.ChrisMeiersMollyNhi.Controllers;

import javafx.event.ActionEvent;

public class GameController extends BaseController {
    public void switchToMainScene(ActionEvent event) throws Exception {
        mainScene.switchScene("/MainScene.fxml");
    }
}