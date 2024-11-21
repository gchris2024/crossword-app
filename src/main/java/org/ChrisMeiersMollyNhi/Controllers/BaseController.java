
package org.ChrisMeiersMollyNhi.Controllers;

import org.ChrisMeiersMollyNhi.CrosswordFXMain;

public abstract class BaseController {
    public CrosswordFXMain mainScene;

    public void setMainScene(CrosswordFXMain mainScene) {
        this.mainScene = mainScene;
    }
}