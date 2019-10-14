package spinbox.commands;

import spinbox.Storage;
import spinbox.lists.TaskList;
import spinbox.Ui;

public class ExitCommand extends Command {
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) {
        this.setExit(true);
        return ui.showGoodbye();
    }
}
