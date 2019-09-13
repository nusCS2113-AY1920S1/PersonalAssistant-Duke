package Duke.Commands;

import Duke.Constant.Duke_Response;
import Duke.Storage;
import Duke.Task.TaskList;
import Duke.Ui;

public class ByeCommand extends Command{

    /**
     * Write all the tasks in TaskList into the file in Storage Class.
     * Sets message of Ui to show that it is shutting down.
     * @param tasks The list of task stored by Duke
     * @param ui The user interface that handles messages
     * @param storage The database to read files and write txt files
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        storage.write(tasks);
        ui.setMessage(new Duke_Response().BYE);
    }

    /**
     * Exits Duke program
     * @return Sets Boolean expression to true
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
