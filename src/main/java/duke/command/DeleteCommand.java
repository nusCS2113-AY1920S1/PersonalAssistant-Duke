package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.util.Optional;

/**
 * duke.command.DeleteCommand which executes the procedure for deleting duke.task.Task objects from the duke.tasklist.TaskList
 */
public class DeleteCommand extends Command {
    private int index;
    Optional<String> filter;

    public DeleteCommand(Optional<String> filter, String index) {
        this.filter = filter;
        this.index = Integer.parseInt(index);
    }

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

    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {
        Task t = tasks.get(filter, index);
        undoStack.addAction(new InsertCommand(filter, index, t));
    }
}