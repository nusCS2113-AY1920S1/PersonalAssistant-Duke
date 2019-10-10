package duke;

import duke.commands.Command;
import duke.commands.ExitCommand;
import duke.commons.exceptions.DukeException;
import duke.parsers.Parser;
import duke.storage.Storage;
import duke.ui.Ui;


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
    public void getResponse(String userInput) {
        try {
            System.out.println("sending to parser");
            //parser.parse(userInput);
            Command c = parser.parse(userInput, ui);

            if (c != null) {
                c.execute(ui, storage);
                if (c instanceof ExitCommand) {
                    tryExitApp();
                }
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            //ui.showError(e.getMessage());
        }


    }

    private void tryExitApp() {
        try {
            main.stop();
        } catch (Exception e) {
            ui.showError("Exit app failed" + e.getMessage());
        }
    }
}
