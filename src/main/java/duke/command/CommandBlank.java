package duke.command;

import duke.task.TaskList;
import duke.worker.Ui;

public class CommandBlank extends Command {
    // Constructor
    public CommandBlank() {
    }

    /**
     * Executes a particular Command.
     */
    @Override
    public void execute(TaskList taskList) {
        Ui.printSeparator();
    }
}
