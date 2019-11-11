package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Grade;
import duchess.model.Module;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.Optional;

/**
 * Command to mark Specified task as complete.
 * Marks are given to be added to the grade.
 */
public class DoneGradeCommand extends Command {
    private double marksObtained;
    private double maxMarks;
    private String moduleCode;
    private int gradeNo;
    private static final String VALID_NUMBER_MSG = "Please supply a valid number.";
    private static final String MODULE_NOT_FOUND_MSG = "Unable to find given module.";


    /**
     * Creates a command to mark given task as complete.
     *
     * @param moduleCode    the code of the module
     * @param gradeNo       the index of the grade in list of grades
     * @param marksObtained marks obtained
     * @param maxMarks      maximum marks obtainable
     */
    public DoneGradeCommand(String moduleCode, int gradeNo, double marksObtained, double maxMarks) {
        this.gradeNo = gradeNo - 1;
        this.moduleCode = moduleCode;
        this.marksObtained = marksObtained;
        this.maxMarks = maxMarks;
    }

    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        try {
            Optional<Module> module = store.findModuleByCode(moduleCode);
            if (module.isPresent()) {
                Grade grade = module.get().getGrades().get(gradeNo);
                grade.markAsComplete(marksObtained, maxMarks);
                module.get().updateGrade(grade);
                ui.showCompletedGrade(grade);
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
