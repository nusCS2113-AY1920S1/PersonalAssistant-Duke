package seedu.duke;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;
import seedu.duke.common.model.Model;
import seedu.duke.common.network.Http;
import seedu.duke.common.network.SimpleServer;
import seedu.duke.ui.UI;


/**
 * The main class of the program, which provides the entry point.
 */
public class Duke {
    private static Duke duke;

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
        SimpleServer.stopServer();
        Model.getInstance().saveModel();
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> Platform.exit());
        delay.play();
    }

    /**
     * Main function of the GUI program.
     */
    private void run() {
        UI.getInstance().initUi();
        Model.getInstance().initModel();
        Http.startAuthProcess();
    }
}
