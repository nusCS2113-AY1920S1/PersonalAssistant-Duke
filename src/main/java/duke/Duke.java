package duke;

import duke.commands.Command;
import duke.commands.ExitCommand;
import duke.commons.exceptions.DukeException;
import duke.parsers.Parser;
import duke.storage.Storage;
import duke.ui.Ui;
import javafx.application.Platform;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * The Duke program is a simple Personal Assistant Chatbot
 * that helps a person to keep track of various things.
 *
 * @author  Jefferson111
 */
public class Duke {
    private static  final String FILE_PATH = "data/tasks.txt";
    private Main main;
    private Ui ui;
    private Storage storage;
    private Parser parser = new Parser();
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    private String reply;

    /**
     * Creates Duke instance.
     */
    public Duke(Main main, Ui ui) {
        this.ui = ui;
        this.main = main;
        ui.showWelcome();
        storage = new Storage(FILE_PATH, ui);
    }

    /**
     * Gets response from Duke.
     *
     * @param userInput The input string from user.
     */
    public Future<Command> getResponse(String userInput) {
        try {
            Future<Command> future = executor.submit(() -> {
                Command c = parser.parse(userInput, ui);
                return c;
            });
            return future;

        } catch (Exception e) {
            ui.showError(e.getMessage());
            return null;
        }


    }

    /**
     * Try to exit program.
     */
    public void tryExitApp() {
        try {
            main.stop();
        } catch (Exception e) {
            ui.showError("Exit app failed" + e.getMessage());
        }
    }

    public String getReply() {
        return parser.getReply();
    }
}
