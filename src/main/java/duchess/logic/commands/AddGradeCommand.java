package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Grade;
import duchess.model.Module;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;
import duchess.log.Log;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to add a given grade to list of grades.
 */
public class AddGradeCommand extends Command {
    private String description;
    private double marks;
    private double maxMarks;
    private double weightage;
    private String moduleCode;
    private final Logger logger;
    private static final String TOTAL_WEIGHTAGE_ERROR = "Total weightage of grades cannot exceed 100.";
    private static final String MODULE_NOT_FOUND_MSG = "Unable to find given module.";

    /**
     * Creates a command to add a grade to a module.
     *
     * @param marks marks obtained
     * @param maxMarks maximum marks obtainable
     * @param weightage weightage of assessment out of 100
     * @param description description of assessment
     * @param moduleCode the code of the module
     */
    public AddGradeCommand(double marks, double maxMarks, double weightage, String description, String moduleCode) {
        assert description != null : "description cannot be empty";
        assert moduleCode != null : "module code cannot be empty";
        this.description = description;
        this.marks = marks;
        this.maxMarks = maxMarks;
        this.weightage = weightage;
        this.moduleCode = moduleCode;
        this.logger = Log.getLogger();
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

        Grade grade = new Grade(description, marks, maxMarks, weightage);
        logger.log(Level.INFO, "Going to add grade : " + grade);
        Optional<Module> module = store.findModuleByCode(moduleCode);
        if (module.isPresent()) {
            if (grade.getWeightage() + module.get().getWeightageTotal() > 100.0) {
                throw new DuchessException(TOTAL_WEIGHTAGE_ERROR);
            }
            module.get().addGrade(grade);
            assert module.get().getWeightageTotal() <= 100 : "weightage should not exceed 100";
            ui.showGradeAdded(module.get(), grade, module.get().getGrades());
            storage.save(store);
            logger.log(Level.INFO, "Added grade : " + grade);
        } else {
            throw new DuchessException(MODULE_NOT_FOUND_MSG);
        }

    }
}



