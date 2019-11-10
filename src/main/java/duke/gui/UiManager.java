package duke.gui;

import duke.Duke;
import javafx.application.Platform;
import javafx.stage.Stage;

//@@author HUANGXUANKUN
/**
 * The manager of the UI component.
 */
public class UiManager implements Ui {

    private MainWindow mainWindow;
    private Duke duke;

    public UiManager(Duke duke) {
        super();
        this.duke = duke;
    }


    @Override
    public void start(Stage primaryStage) {
        try {
            mainWindow = new MainWindow(primaryStage, duke);
            mainWindow.show(); //This should be called before creating other UI parts
        } catch (Throwable e) {
            System.out.println("UiManager start() error: " + e.getMessage());
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }


    /**
     * Exits the application after fatal error.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        // Raise an error alert here with title.
        Platform.exit();
        System.exit(1);
    }

}
