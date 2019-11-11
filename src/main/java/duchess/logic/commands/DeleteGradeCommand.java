package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Grade;
import duchess.model.Module;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.Optional;

/**
 * Command to delete grade from given module.
 */
public class DeleteGradeCommand extends Command {
    private final String moduleCode;
    private final int gradeNo;
    private static final String VALID_NUMBER_MSG = "Please supply a valid number.";
    private static final String MODULE_NOT_FOUND_MSG = "Unable to find given module.";

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
            throw new DuchessException(VALID_NUMBER_MSG);
        } catch (IllegalArgumentException e) {
            throw new DuchessException(MODULE_NOT_FOUND_MSG);
        }
    }
}
