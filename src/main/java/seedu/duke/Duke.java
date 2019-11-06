package seedu.duke;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import seedu.duke.common.model.Model;
import seedu.duke.common.network.Http;
import seedu.duke.common.network.SimpleServer;
import seedu.duke.common.storage.ResourceHelper;
import seedu.duke.common.storage.StorageHelper;
import seedu.duke.ui.UI;

import java.util.logging.Logger;
import seedu.duke.common.logger.LogsCenter;

/**
 * The main class of the program, which provides the entry point.
 */
public class Duke {
    private static Duke duke;
    private static final Logger logger = LogsCenter.getLogger(Main.class);

    private Duke() {
        run();
    }

    /**
     * Gets new instance.
     *
     * @return duke
     */
    public static Duke getInstance() {
        if (duke == null) {
            duke = new Duke();
        }
        return duke;
    }

    /**
     * Exits the entire program.
     */
    public void exit() {
        logger.info("=============================[ Exiting Email Manager "
                + "]===========================");
        logger.info("Stopping Server");
        SimpleServer.stopServer();
        logger.info("Saving Model");
        Model.getInstance().saveModel();
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> Platform.exit());
        delay.play();
    }

    /**
     * Main function of the GUI program.
     */
    private void run() {
        logger.info("preparing data files");
        StorageHelper.constructDataDirectory();
        //ResourceHelper.prepareTestFile();
        logger.info("initializing UI");
        UI.getInstance().initUi();
        logger.info("initializing Model");
        Model.getInstance().initModel();
        logger.info("Starting server to fetch emails");
        Http.startAuthProcess();
    }
}
