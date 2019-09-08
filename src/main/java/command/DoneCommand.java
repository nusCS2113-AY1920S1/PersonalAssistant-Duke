package command;
import process.*;
import process.DukeException;
import task.TaskList;

/**
 * Represents a command that checks items as done in tasks
 */
public class DoneCommand extends Command {
    private int index;
    public DoneCommand(int index) {
        this.index = index;
    }
    /**
     * Executes the DoneCommand
     */
    public String execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (this.index >= tasks.size()) throw new DukeException("index error");
        tasks.doneTask(this.index);
        String output = ui.showTaskDone(tasks.get(this.index).toString());
        storage.save(tasks);
        return output;
    }
}
