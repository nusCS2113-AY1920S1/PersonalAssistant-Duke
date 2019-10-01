package duke;

import duke.ui.MainWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {
    /**
     * Starts duke GUI.
     * @param stage the stage for GUI.
     */
    @Override
    public void start(Stage stage) {
        System.out.println("Starting Duke...");
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
