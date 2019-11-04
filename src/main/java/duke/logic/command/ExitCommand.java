package duke.logic.command;

import java.io.IOException;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * duke.logic.parser.command.ExitCommand class which will save all the duke.task.
 * Task data into the JSON file and initiate the exit procedure
 */
public class ExitCommand extends Command {

    /**
     * Saves TaskList of user tasks to the JSON data file
     *
     * @param tasks TaskList of all of user's tasks
     * @param ui Ui handling user interaction
     * @param storage Storage handling saving and loading of TaskList
     * @throws IOException NA
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
        storage.save(tasks);
    }

    /**
     * Not applicable for this Command.
     * @param tasks NA
     * @param undoStack NA
     * @throws DukeException NA
     */
    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {

    }

    /**
     * Method to initialise the process of closing the application
     *
     * @return boolean true to initialise closing the application
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
