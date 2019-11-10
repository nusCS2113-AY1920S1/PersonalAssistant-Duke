package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Module;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.Optional;

/**
 * Command to list grades for the given module.
 */
public class ListGradesCommand extends Command {
    private String moduleCode;

    public ListGradesCommand(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        Optional<Module> module = store.findModuleByCode(moduleCode);
        if (module.isPresent()) {
            ui.showGradeList(module.get().getGrades(), module.get());
        } else {
            throw new DuchessException("Unable to find given module.");
        }
    }
}
