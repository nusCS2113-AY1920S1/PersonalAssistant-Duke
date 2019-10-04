package duchess.logic.commands;

import duchess.exceptions.DukeException;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;
import duchess.model.Module;

import java.util.List;

public class AddModuleCommand extends Command {
    private String moduleCode;
    private String moduleName;

    /**
     * Create a command to add a module.
     *
     * @param words arguments supplied by the user
     */
    public AddModuleCommand(List<String> words) throws DukeException {
        if (words.size() < 2) {
            throw new DukeException("Usage: add modules <module code> <module name>");
        }

        moduleCode = words.get(0);
        moduleName = String.join(" ", words.subList(1, words.size()));
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DukeException {
        Module module = new Module(moduleCode, moduleName);
        store.getModuleList().add(module);
        ui.showModuleAdded(module, store.getModuleList());
    }
}
