package duke.logic.command;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * Mirror command class for both EditCommand and DoneCommand
 * Initialised with a copy of the Task before it is edited or marked done
 * When this is executed, it will replace the new copy of the Task with the original.
 */
public class SetCommand extends Command {
    private Optional<String> filter;
    private int index;
    private Task t;

    /**
     * Constructor for SetCommand
     * Stores the filter and index of the original Task in the TaskList and also the original copy of the Task
     * before it was edited or marked as done
     *
     * @param filter filter for each task
     * @param index index of the task
     * @param t original version of the task before editing or marking done
     */
    public SetCommand(Optional<String> filter, int index, Task t) {
        this.filter = filter;
        this.index = index;
        this.t = t;
    }

    /**
     * Replaces the new task with its original version.
     *
     * @param tasks TaskList containing all of the user's tasks
     * @param ui Ui handling user interactions
     * @param storage Storage handling the saving and loading of the TaskList
     * @throws IOException NA
     * @throws ParseException NA
     * @throws DukeException if the given index is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, ParseException, DukeException {
        tasks.set(filter, index, t);
        ui.showLine(t + " has successfully been reverted to its previous state!");
        storage.save(tasks);
    }

    /**
     * Not applicable for this class
     * @param tasks NA
     * @param undoStack NA
     * @throws DukeException NA
     */
    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {
    }
}
