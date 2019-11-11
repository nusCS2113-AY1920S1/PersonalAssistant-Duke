package duke.logic.command;

import java.io.IOException;
import java.text.ParseException;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * Class that handles the execution of undoing the most recent task that can be undone
 */
public class UndoCommand extends Command {
    private UndoStack undoStack;

    /**
     * Constructor for UndoCommand
     * Initialised with the current UndoStack in this user session
     *
     * @param undoStack the current UndoStack in this user session
     */
    public UndoCommand(UndoStack undoStack) {
        this.undoStack = undoStack;
    }

    /**
     * If UndoStack is empty, tells user that there are no actions to undo.
     * Else, executes the process of undoing the most recent command that can be undone
     *
     * @param tasks   TaskList of all the user tasks
     * @param ui      Ui handling user interactions
     * @param storage Storage handling saving and loading of the TaskList
     * @throws IOException    NA
     * @throws ParseException NA
     * @throws DukeException  if there are no actions to undo
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, ParseException, DukeException {
        if (undoStack.isEmpty()) {
            throw new DukeException("There are no actions to undo!");
        }
        Command mostRecent = undoStack.retrieveRecent();
        mostRecent.execute(tasks, ui, storage);
    }

    /**
     * Not applicable for this Command.
     *
     * @param tasks     NA
     * @param undoStack NA
     * @throws DukeException NA
     */
    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {

    }
}
