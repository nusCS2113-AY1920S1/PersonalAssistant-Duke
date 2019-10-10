package duke;

import duke.commands.Command;
import duke.commons.exceptions.DukeException;
import duke.parsers.Parser;
import duke.ui.Ui;

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
    private Parser parser = new Parser();
    private ExecutorService executor = Executors.newFixedThreadPool(1);

    /**
     * Creates Duke instance.
     */
    public Duke(Main main, Ui ui) {
        this.ui = ui;
        this.main = main;
        ui.showWelcome();
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
            ui.showError(e.toString());
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

    public String getPrompt() {
        return parser.getPrompt();
    }
}
