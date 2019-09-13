package duke.commands;

import duke.constant.DukeResponse;
import duke.Storage;
import duke.task.TaskList;
import duke.Ui;

public class ByeCommand extends Command {

    /**
     * Write all the tasks in TaskList into the file in Storage Class.
     * Sets message of Ui to show that it is shutting down.
     * @param tasks The list of task stored by duke
     * @param ui The user interface that handles messages
     * @param storage The database to read files and write txt files
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        storage.write(tasks);
        ui.setMessage(new DukeResponse().BYE);
    }

    /**
     * Exits duke program.
     * @return Sets Boolean expression to true
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
