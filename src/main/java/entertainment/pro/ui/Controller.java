package entertainment.pro.ui;

import entertainment.pro.Main;
import javafx.stage.Stage;

/**
 * Abstract class that contains the basic parameters for controllers which is the window and application.
 */
public abstract class Controller {
    protected Stage mWindow;
    protected Main mMainApplication;

    /**
     * Responsible for setting the window for this controller
      */
    public void setWindow(Stage window) {
        mWindow = window;
    }

    /**
     * Responsible for setting the main application for this controller.
     * @param mainApplication The main application to be set for this controller.
     */
    public void setMainApplication(Main mainApplication) {
        mMainApplication = mainApplication;
    }
}

