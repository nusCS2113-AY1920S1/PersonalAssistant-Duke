package duke.logic.command;

import java.io.IOException;

import duke.exception.DukeException;
import duke.logic.TaskAssigner;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * Class that handles the auto assigning task to some existing filter
 */
public class AutoAssignCommand extends Command {
    private int index;

    /**
     * Constructor for AutoAssignCommand
     *
     * @param index
     */
    public AutoAssignCommand(String index) throws DukeException {
        try {
            this.index = Integer.parseInt(index) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a numerical field for the index!");
        }
    }

    /**
     * Executes auto assignment of a task to some existing filter
     *
     * @param tasks   TaskList of all of user's tasks
     * @param ui      Ui handling user interaction
     * @param storage Storage handling saving and loading of TaskList
     * @throws IOException
     * @throws DukeException
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        TaskAssigner.assign(tasks, tasks.get(index));
        storage.save(tasks);
    }

    /**
     * @param tasks
     * @param undoStack
     * @throws DukeException
     */
    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {

    }
}
