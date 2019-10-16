package spinbox.commands;

import spinbox.containers.ModuleContainer;
import spinbox.entities.Module;
import spinbox.Storage;
import spinbox.containers.lists.TaskList;
import spinbox.Ui;

import java.util.ArrayDeque;
import java.util.HashMap;

public class ExitCommand extends Command {
    // Old execute
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) {
        this.setExit(true);
        return ui.showGoodbye();
    }

    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui) {
        this.setExit(true);
        return ui.showGoodbye();
    }
}
