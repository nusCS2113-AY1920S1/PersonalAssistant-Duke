package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Grade;
import duchess.model.Module;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.Optional;

public class DeleteGradeCommand extends Command {
    private final String moduleCode;
    private final int gradeNo;

    public DeleteGradeCommand(String moduleCode, int gradeNo) {
        this.gradeNo = gradeNo - 1;
        this.moduleCode = moduleCode;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        try {
            Optional<Module> module = store.findModuleByCode(moduleCode);
            if (module.isPresent()) {
                Grade toRemove = module.get().getGrades().get(gradeNo);
                module.get().deleteGrade(gradeNo);
                ui.showDeletedGrade(toRemove.getTask(), module.get());
                storage.save(store);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IndexOutOfBoundsException e) {
            throw new DuchessException("Please supply a valid number.");
        } catch (IllegalArgumentException e) {
            throw new DuchessException("Unable to find given module.");
        }
    }
}
