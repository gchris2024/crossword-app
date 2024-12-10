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
 * Class: BaseController
 *
 * Description: Base controller for all controllers in the project.
 * This is a template that can be used to create new controllers.
 *
 * ****************************************
 */


package org.ChrisMeiersMollyNhi.Controllers;

import org.ChrisMeiersMollyNhi.CrosswordFXMain;

public abstract class BaseController {
    public CrosswordFXMain mainScene;

    public void setMainScene(CrosswordFXMain mainScene) {
        this.mainScene = mainScene;
    }
}