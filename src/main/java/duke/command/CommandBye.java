package duke.command;

import duke.task.TaskList;
import duke.worker.Ui;

public class CommandBye extends Command {

    // Constructor
    public CommandBye() {
    }

    @Override
    public void execute(TaskList taskList) {
        this.exitRequest = true;
        Ui.dukeSays("Bye. Hope to see you again soon!");
    }
}
