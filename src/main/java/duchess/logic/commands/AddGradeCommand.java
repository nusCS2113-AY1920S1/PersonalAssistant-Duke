package duchess.logic.commands;

import duchess.exceptions.DuchessException;
import duchess.model.Grade;
import duchess.model.Module;
import duchess.storage.Storage;
import duchess.storage.Store;
import duchess.ui.Ui;

import java.util.Optional;

public class AddGradeCommand extends Command {
    private String assessment;
    private int marks;
    private int maxMarks;
    private int weightage;
    private String moduleCode;


    /**
     * Creates a command to add a grade to a module.
     *
     * @param marks marks obtained
     * @param maxMarks maximum marks obtainable
     * @param weightage weightage of assessment out of 100
     * @param assessment description of assessment
     * @param moduleCode the code of the module
     */
    public AddGradeCommand(int marks, int maxMarks, int weightage, String assessment, String moduleCode) {
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
     * @throws DuchessException if module cannot be found
     */
    @Override
    public void execute(Store store, Ui ui, Storage storage) throws DuchessException {
        Grade grade = new Grade(assessment, marks, maxMarks, weightage);
        Optional<Module> optionalModule = store.findModuleByCode(moduleCode);
        Module module = optionalModule.orElseThrow(() -> new DuchessException("Unable to find given module."));
        module.addGrade(grade);
        ui.showGradeAdded(module, grade, module.getGrades());
        storage.save(store);
    }
}



