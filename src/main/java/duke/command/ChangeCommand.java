package duke.command;

import duke.util.Reminder;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

public class ChangeCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage store, Reminder reminder) {

    }

    public void execute() {

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
