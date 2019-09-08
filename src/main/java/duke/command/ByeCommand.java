package duke.command;

import duke.task.TaskList;
import duke.dukeexception.DukeException;

public class ByeCommand extends Command {
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        this.isExit = true;
        ui.showBye();
    }
}
