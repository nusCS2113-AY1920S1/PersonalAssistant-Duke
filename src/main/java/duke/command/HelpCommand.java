package duke.command;

import duke.core.DukeException;
import duke.core.Storage;
import duke.task.TaskList;
import duke.core.Ui;

public class HelpCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        ui.showHelpCommand();

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
