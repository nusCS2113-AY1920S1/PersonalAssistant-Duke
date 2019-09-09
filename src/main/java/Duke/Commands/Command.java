package Duke.Commands;

import Duke.Storage;
import Duke.Task.TaskList;
import Duke.Ui;

public abstract class Command {
    protected boolean quit;

    /**
     * @param tasks The list of task stored by Duke
     * @param ui The user interface that handles messages
     * @param storage The database to read files and write txt files
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    /**
     * Duke continues to run.
     * @return Sets boolean expression to false.
     */
    public boolean isExit(){
        return false;
    }
}
