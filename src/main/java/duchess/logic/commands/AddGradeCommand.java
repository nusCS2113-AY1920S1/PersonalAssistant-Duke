package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Grade;
import duchess.model.Module;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.Optional;

/**
 * Command to add a given grade to list of grades.
 */
public class AddGradeCommand extends Command {
    private String assessment;
    private double marks;
    private double maxMarks;
    private double weightage;
    private String moduleCode;
    private static final String TOTAL_WEIGHTAGE_ERROR = "Total weightage of grades cannot exceed 100.";

    /**
     * Creates a command to add a grade to a module.
     *
     * @param marks marks obtained
     * @param maxMarks maximum marks obtainable
     * @param weightage weightage of assessment out of 100
     * @param assessment description of assessment
     * @param moduleCode the code of the module
     */
    public AddGradeCommand(double marks, double maxMarks, double weightage, String assessment, String moduleCode) {
        this.assessment = assessment;
        this.marks = marks;
        this.maxMarks = maxMarks;
        this.weightage = weightage;
        this.moduleCode = moduleCode;
    }

    /**
     * Adds a grade to a module.
     *
     * @param store the store
     * @param ui Userinterface object
     * @param storage Storage object
     * @throws DuchessException if module cannot be found or if total weightage exceeds 100
     */
    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        Grade grade = new Grade(assessment, marks, maxMarks, weightage);
        Optional<Module> module = store.findModuleByCode(moduleCode);
        if (module.isPresent()) {
            if (grade.getWeightage() + module.get().getWeightageTotal() > 100.0) {
                throw new DuchessException(TOTAL_WEIGHTAGE_ERROR);
            }
            module.get().addGrade(grade);
            ui.showGradeAdded(module.get(), grade, module.get().getGrades());
            storage.save(store);
        } else {
            throw new DuchessException("Unable to find given module.");
        }
    }
}



