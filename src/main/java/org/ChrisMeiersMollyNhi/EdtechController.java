
package org.ChrisMeiersMollyNhi;

import javafx.event.ActionEvent;

public class EdtechController extends BaseController {
    public void switchToMainScene(ActionEvent event) throws Exception {
        mainScene.switchScene("/MainScene.fxml");
    }
}