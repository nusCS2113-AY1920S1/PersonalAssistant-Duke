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
 * duke.logic.parser.command.DoneCommand that deals with marking Tasks in the duke.tasklist.TaskList as done
 */
public class DoneCommand extends Command {
    private Optional<String> filter;
    private int index;

    /**
     * Constructor of DoneCommand
     * Includes filter and index to find the location of the task in the actual TaskList
     *
     * @param filter filter for each task
     * @param index  given index of the task
     */
    public DoneCommand(Optional<String> filter, String index) throws DukeException {
        this.filter = filter;
        try {
            this.index = Integer.parseInt(index) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a numerical field for the index!");
        }
    }

    /**
     * Executes the marking of a Task as done
     *
     * @param tasks   TaskList of all of user's tasks
     * @param ui      Ui handling user interaction
     * @param storage Storage handling saving and loading of TaskList
     * @throws DukeException if invalid index is given
     * @throws IOException   NA
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        Task t = tasks.get(filter, index);
        if (t.getIsDone()) {
            ui.showLine("Congratulations you've already completed this task! Go move on and do other task stop "
                    + "staying in the past!");
        } else {
            t.markAsDone();
            ui.showLine("Congratulations on completing the following task:");
            ui.showLine(t.getDescription());
            storage.save(tasks);
        }
    }

    /**
     * Saves a mirror command in the UndoStack
     * In the event that the user marked the wrong task as done, he can just call undo to undo the task
     *
     * @param tasks     TaskList of all of user's tasks
     * @param undoStack Storage handling saving and loading of TaskList
     * @throws DukeException
     */
    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {
        Task t = tasks.get(filter, index);
        undoStack.addAction(new SetCommand(filter, index, new Task(t)));
        System.out.println(t);
    }
}
