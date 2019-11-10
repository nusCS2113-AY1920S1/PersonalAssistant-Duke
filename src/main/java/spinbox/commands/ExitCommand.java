package spinbox.commands;

import spinbox.containers.ModuleContainer;
import spinbox.Ui;

import java.util.ArrayDeque;

public class ExitCommand extends Command {
    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui, boolean guiMode) {
        this.setExit(true);
        return ui.showGoodbye();
    }
}
