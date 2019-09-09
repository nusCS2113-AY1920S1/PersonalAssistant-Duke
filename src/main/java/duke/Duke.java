package duke;

import duke.commands.Command;
import duke.commands.ExitCommand;
import duke.commons.DukeException;
import duke.parsers.Parser;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * The duke.Duke program is a simple Personal Assistant Chatbot
 * that helps a person to keep track of various things.
 *
 * @author  Jefferson111
 */
public class Duke {
    private static  final String FILE_PATH = "tasks.txt";

    /**
     * Entry point.
     */
    public static void main(String[] args) {
        new Duke();
    }

    /**
     * Creates duke.Duke instance.
     */
    private Duke() {
        Ui ui = new Ui();
        ui.showWelcome();
        Storage storage = new Storage(FILE_PATH, ui);
        while (true) {
            String userInput = ui.readCommand();
            try {
                Command command = Parser.parse(userInput);
                command.execute(ui, storage);
                if (command instanceof ExitCommand) {
                    break;
                }
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}
