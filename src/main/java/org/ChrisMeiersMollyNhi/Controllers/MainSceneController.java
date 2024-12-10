/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Fall 2024
 * Instructor: Prof. Lily
 *
 * Name: Molly Yoder
 * Section: 10am
 * Date: 11/14/2024
 * Time: 1:09 PM
 *
 * Project: csci205_final_project
 * Package: org.ChrisMeiersMollyNhi
 * Class: MainSceneController
 *
 * Description: This class is the controller for the main scene.
 * It handles switching to the edtech or game scenes when buttons are clicked.
 *
 * ****************************************
 */

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