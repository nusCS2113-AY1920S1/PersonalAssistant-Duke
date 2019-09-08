package command;
import process.*;

import process.DukeException;
import task.TaskList;

/**
 * Represents a command that deletes an item from tasks
 */
public class DeleteCommand extends Command {
    private int index;
    /**
     * Creates a new DeleteCommand object with the given index
     * @param index of the task to be deleted
     */
    public DeleteCommand(int index) {
        this.index = index;
    }
    /**
     * Executes the DeleteCommand and saves changes to storage
     */
    public String execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (this.index >= tasks.size()) throw new DukeException("index error");
        String output = ui.showTaskDelete(tasks.get(this.index).toString(),tasks.size()-1);
        tasks.deleteTask(index);
        storage.save(tasks);
        return output;
    }
}
