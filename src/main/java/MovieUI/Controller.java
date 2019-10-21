package MovieUI;

import javafx.stage.Stage;

/**
 * Abstract class that contains the basic parameters for controllers which is the window and application.
 */
public abstract class Controller {
    protected Stage mWindow;
    protected Main mMainApplication;

    // Sets the window for this controller
    public void setWindow(Stage window)
    {
        mWindow = window;
    }

    // Sets the main application for this controller
    public void setMainApplication(Main mainApplication)
    {
        mMainApplication = mainApplication;
    }

}

