package entertainment.pro.ui;

import entertainment.pro.Main;
import javafx.stage.Stage;

/**
 * Abstract class that contains the basic parameters for controllers which is the window and application.
 */
public abstract class Controller {
    protected Stage stageWindow;
    protected Main mainApplication;

    /**
     * Responsible for setting the window for this controller.
      */
    public void setWindow(Stage window) {
        stageWindow = window;
    }

    /**
     * Responsible for setting the main application for this controller.
     * @param application The main application to be set for this controller.
     */
    public void setMainApplication(Main application) {
        mainApplication = application;
    }
}

