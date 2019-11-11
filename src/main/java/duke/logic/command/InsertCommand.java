package duke.logic.command;

import java.util.Optional;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * Class that handles inserting a Task at a certain index of the TaskList
 */
public class InsertCommand extends Command {
    private Optional<String> filter;
    private int index;
    private Task t;

    /**
     * Constructor of InsertCommand
     * Initialised with a filter and index to confirm the location in the TaskList where the Task t should be inserted
     *
     * @param filter filter for each task
     * @param index  index of the given task
     * @param t      task to be inserted
     */
    public InsertCommand(Optional<String> filter, int index, Task t) {
        this.filter = filter;
        this.index = index;
        this.t = t;
    }

    /**
     * Executes the insertion of the task into the specified index
     *
     * @param tasks   TaskList of all of user's tasks
     * @param ui      Ui handling user interaction
     * @param storage Storage handling saving and loading of TaskList
     * @throws DukeException if given index is valid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        tasks.insert(filter, index, t);
        ui.showLine(t + " has been added back into your task list!");
    }

    /**
     * Not applicable for this Command.
     *
     * @param tasks     NA
     * @param undoStack NA
     */
    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) {
    }
}
