package duke.commands;

import duke.Storage;
import duke.TaskList;
import duke.Ui;

public class ExitCommand extends Command {
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) {
        this.setExit(true);
        return ui.showGoodbye();
    }
}
