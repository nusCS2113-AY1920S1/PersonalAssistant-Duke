package duke;

import duke.command.Command;
import duke.exception.DukeException;

/**
 * Represents a Personal Assistant bot. A <code>Duke</code> object corresponds to three other classes
 * , namely called <code>Storage</code>, <code>Ui</code> and <code>TaskList</code>.
 */
public class Duke {

    private Storage storage;
    private TaskList arr;
    private Ui ui;

    /**
     * Constructor for <code>Duke</code> for instantiation of other classes <code>Ui</code>
     * , <code>Storage</code> and <code>Tasklist</code>.
     */
    public Duke() {
        ui = new Ui();
        storage = new Storage();
        try {
            arr = new TaskList(storage.readFromFile());
        }
        catch (DukeException exception) {
            ui.showLoadingError(exception);
            arr = new TaskList();
        }
    }

    /**
     * Runs the Personal Assistant.
     */
    public void run() {
        ui.showWelcome();
        ui.hello();
        boolean isExit = false;
        while (!isExit) {
            try {
                String line = ui.scanLine();
                Command command = chooseCommand.choose(line);
                command.execute(arr, ui, storage);
                isExit = command.isExit();
            }
            catch (DukeException exception) {
                ui.showLoadingError(exception);
            }
        }
    }

    /**
     * This is the main method which makes use of run method.
     * @param args Unused.
     */
    public static void main(String[] args) {
        new Duke().run();
    }
}