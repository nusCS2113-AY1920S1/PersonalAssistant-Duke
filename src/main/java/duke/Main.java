package duke;

import duke.gui.Ui;
import duke.gui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

//@@author HUANGXUANKUN
/**
 * Launcher class for the app.
 */
public class Main extends Application {
    private Ui ui;
    private Duke duke;

    @Override
    public void init() throws Exception {
        super.init();
        initialize();
    }

    /**
     * Initialize Duke with local data.
     * Create data directory if it is not existed.
     */
    private void initialize() {
        String currentDir = System.getProperty("user.dir");
        String dataDir = currentDir + "/data";
        File directory = new File(dataDir);
        if (!directory.exists()) {
            directory.mkdir();
        }
        duke = new Duke(dataDir);
        ui = new UiManager(duke);
    }

    /**
     * It initialize the UI manager to initialize GUI components for stages and load data from local hard disk.
     *
     * @param primaryStage The main stage MainWindow, which is used to contain all GUI components
     */
    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }
}
