package duke;

import duke.ui.MainWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An app called SGTravel.
 */
public class Main extends Application {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Starts SGTravel GUI.
     *
     * @param stage the stage for GUI.
     */
    @Override
    public void start(Stage stage) {
        logger.log(Level.INFO, "SGTravel starting up...");
        try {
            MainWindow mainWindow = new MainWindow(stage);
            mainWindow.show();
            mainWindow.initialise(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the GUI.
     *
     * @throws Exception The exception thrown.
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch();
    }
}
