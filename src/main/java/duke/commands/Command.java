package duke.commands;

import duke.Storage;
import duke.task.TaskList;
import duke.Ui;

public abstract class Command {
    protected boolean quit;

    /**
     * Execute corresponding command and store response to Ui.
     * @param tasks The list of task stored by duke
     * @param ui The user interface that handles messages
     * @param storage The database to read files and write txt files
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);

    /**
     * duke continues to run.
     * @return Sets boolean expression to false.
     */
    public boolean isExit() {
        return false;
    }
}
