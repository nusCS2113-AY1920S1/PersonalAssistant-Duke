package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Module;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.Optional;

public class ListGradesCommand extends Command {
    private String moduleCode;

    public ListGradesCommand(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        Optional<Module> optionalModule = store.findModuleByCode(moduleCode);
        Module module = optionalModule.orElseThrow(() -> new DuchessException("Unable to find given module."));
        ui.showGradeList(module.getGrades(), module);
    }
}
