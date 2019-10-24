package duke.ui;

import duke.commons.LogsCenter;
import duke.exception.DukeException;
import duke.logic.Logic;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class UiManager implements Ui {

    private Logic logic;
    private MainWindow mainWindow;

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    public UiManager(Logic logic) {
        this.logic = logic;
    }

    @Override
    public void start(Stage primaryStage) {
        mainWindow = new MainWindow(primaryStage, logic);

        mainWindow.show();

        mainWindow.fillInnerPart(); // ***
        logger.info("MainWindow are showed and filled in.");
    }
}
