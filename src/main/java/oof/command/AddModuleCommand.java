package oof.command;

import java.util.ArrayList;

import oof.SelectedInstance;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

public class AddModuleCommand extends Command {

    public static final String COMMAND_WORD = "module";
    private ArrayList<String> arguments;
    private static final int INDEX_CODE = 0;
    private static final int INDEX_NAME = 1;
    private static final int ARRAY_SIZE_NAME = 2;

    /**
     * Constructor for AddModuleCommand.
     *
     * @param arguments Command inputted by user for processing.
     */
    public AddModuleCommand(ArrayList<String> arguments) {
        super();
        this.arguments = arguments;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, StorageManager storageManager)
            throws OofException {
        if (arguments.get(INDEX_CODE).isEmpty()) {
            throw new OofException("OOPS!! The module needs a code.");
        } else if (arguments.size() < ARRAY_SIZE_NAME || arguments.get(INDEX_NAME).isEmpty()) {
            throw new OofException("OOPS!! The module needs a name.");
        }
        String moduleName = arguments.get(INDEX_NAME);
        String moduleCode = arguments.get(INDEX_CODE);
        String description = moduleCode + " " + moduleName;
        if (exceedsMaxLength(description)) {
            throw new OofException("Task exceeds maximum description length!");
        }
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Semester semester = selectedInstance.getSemester();
        if (semester == null) {
            throw new OofException("OOPS!! Please select a semester!");
        }
        Module module = new Module(moduleCode, moduleName);
        semester.addModule(module);
        ui.printModuleAddedMessage(module);
        selectedInstance.selectModule(module);
        storageManager.writeSemesterList(semesterList);
    }
}
