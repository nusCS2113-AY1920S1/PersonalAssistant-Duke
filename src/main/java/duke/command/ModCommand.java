package duke.command;

import duke.exceptions.ModException;
import duke.util.Reminder;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

public class ModCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage store, Reminder reminder) throws ModException {
    }

    @Override
    public boolean isExit() {
        return false;
    }

}
