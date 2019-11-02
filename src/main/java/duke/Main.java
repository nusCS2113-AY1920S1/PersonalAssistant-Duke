package duke;

import duke.gui.Ui;
import duke.gui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Launcher class for the app.
 */
public class Main extends Application {
    private Ui ui;
    private Duke duke;

    @Override
    public void init() throws Exception {
        super.init();

        String currentDir = System.getProperty("user.dir");
        duke = new Duke(currentDir);
        ui = new UiManager(duke);
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }
}
