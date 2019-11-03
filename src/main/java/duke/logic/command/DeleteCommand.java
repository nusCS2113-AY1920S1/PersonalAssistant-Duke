package duke.logic.command;

import java.io.IOException;
import java.util.Optional;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * duke.logic.parser.command.DeleteCommand which executes the procedure for
 * deleting duke.task.Task objects from the duke.tasklist.TaskList
 */
public class DeleteCommand extends Command {
    private Optional<String> filter;
    private int index;

    /**
     * Constructor for DeleteCommand
     * Filter and index enable the locating of the actual location of the task in TaskList
     * @param filter filter for each task
     * @param index given index of the task
     */
    public DeleteCommand(Optional<String> filter, String index) throws DukeException {
        this.filter = filter;
        try {
            this.index = Integer.parseInt(index) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a numerical field for the index!");
        }
    }

    /**
     * Executes teh deletion of the Task at the given location from the TaskList
     *
     * @param tasks TaskList of all of user's tasks
     * @param ui Ui handling user interaction
     * @param storage Storage handing saving and loading of TaskList
     * @throws DukeException if invalid index is given
     * @throws IOException NA
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        ui.showLine("You have removed this task:");
        Task t = tasks.get(filter, index);
        ui.showLine(t.getDescription());
        tasks.remove(filter, index);
        if (tasks.size() == 1) {
            ui.showLine("Now you have 1 task in the list.");
        } else {
            ui.showLine("Now you have " + tasks.size() + " tasks in the list.");
        }
        storage.save(tasks);
    }

    /**
     * Adds a mirror command to the UndoStack
     * Can undo the DeleteCommand by creating a new InsertCommand at the same index containing the original task
     * @param tasks TaskList of all of user's tasks
     * @param undoStack UndoStack containing all the mirror commands
     * @throws DukeException if invalid index is given
     */
    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {
        Task t = tasks.get(filter, index);
        undoStack.addAction(new InsertCommand(filter, index, t));
    }
}
