import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import duke.logic.command.Command;
import duke.exception.DukeException;
import duke.logic.parser.DuqueParser;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * Main class which is responsible for the running of the task manager
 */
public class Duke {
    private Ui ui;
    private Storage storage;
    private TaskList tasks;
    private UndoStack undoStack;

    public Duke(String filepath){
        ui = new Ui();
        undoStack = new UndoStack();
        storage = new Storage(filepath);
        try {
            tasks = new TaskList(storage.load());
        } catch (FileNotFoundException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public static void main(String[] args) {
        new Duke("duke.json").run();
    }

    /**
     * function that starts the program
     * maintains the program while the exit command is not given
     * exits program once exit command is given
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = DuqueParser.parseCommand(fullCommand, undoStack);
                c.savePrevState(tasks, undoStack);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (ParseException e) {
                ui.showParsingError();
            } catch (DukeException | IOException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.exit();
    }
}
