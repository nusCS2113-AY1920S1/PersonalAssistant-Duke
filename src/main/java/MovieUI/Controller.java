package MovieUI;

import javafx.stage.Stage;

public abstract class Controller
{
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

