package duke;

import duke.command.Command;
import duke.exception.DukeException;

import java.io.IOException;

/**
 * Represents a Personal Assistant bot. A Duke object corresponds to three other classes,
 * namely called Storage, Ui and TaskList.
 */
public class Duke {

    private Storage storage;
    private TaskList arr;
    private Ui ui;
    private Reminder reminder;

    /**
     * Constructor for Duke for instantiation of other classes Ui, Storage and TaskList.
     */
    public Duke() {
        ui = new Ui();
        storage = new Storage();
        reminder = new Reminder();
        try {
            arr = new TaskList(storage.readFromFile());
        } catch (IOException e) {
            arr = new TaskList();
        }
    }

    /**
     * Runs the Personal Assistant.
     */
    public void run() {
        ui.showWelcome();
        ui.hello();
        reminder.checkDeadline(arr, ui);
        boolean isExit = false;
        while (!isExit) {
            try {
                String line = ui.scanLine();
                Command command = ChooseCommand.choose(line);
                command.execute(arr, ui, storage);
                isExit = command.isExit();
            } catch (DukeException exception) {
                ui.showLoadingError(exception);
            }
        }
    }

    /**
     * This is the main method which makes use of run method.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        new Duke().run();
    }
}