package spinbox.commands;

import spinbox.containers.ModuleContainer;
import spinbox.Storage;
import spinbox.containers.lists.TaskList;
import spinbox.Ui;

import java.util.ArrayDeque;

public class ExitCommand extends Command {
    // Old execute
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui, boolean gui) {
        this.setExit(true);
        return ui.showGoodbye();
    }

    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui, boolean guiMode) {
        this.setExit(true);
        return ui.showGoodbye();
    }
}
