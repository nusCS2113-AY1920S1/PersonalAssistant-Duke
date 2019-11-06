package oof.command;

import java.util.ArrayList;

import oof.SelectedInstance;
import oof.Ui;
import oof.exception.command.CommandException;
import oof.exception.command.InvalidArgumentException;
import oof.exception.command.MissingArgumentException;
import oof.exception.command.SemesterNotSelectedException;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

//@@author KahLokKee

public class AddModuleCommand extends Command {

    public static final String COMMAND_WORD = "module";
    private ArrayList<String> arguments;
    private static final int INDEX_CODE = 0;
    private static final int INDEX_NAME = 1;
    private static final int ARRAY_SIZE_NAME = 2;
    private static final int DESCRIPTION_LENGTH_MAX = 50;


    /**
     * Constructor for AddModuleCommand.
     *
     * @param arguments Command inputted by user for processing.
     */
    public AddModuleCommand(ArrayList<String> arguments) {
        super();
        this.arguments = arguments;
    }

    /**
     * Adds a module to semester.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param tasks          Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws CommandException if semester is not selected or if user input contains missing or invalid arguments.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, StorageManager storageManager)
            throws CommandException {
        if (arguments.get(INDEX_CODE).isEmpty()) {
            throw new MissingArgumentException("OOPS!! The module needs a code.");
        } else if (arguments.size() < ARRAY_SIZE_NAME || arguments.get(INDEX_NAME).isEmpty()) {
            throw new MissingArgumentException("OOPS!! The module needs a name.");
        }
        String moduleName = arguments.get(INDEX_NAME);
        String moduleCode = arguments.get(INDEX_CODE);
        String description = moduleCode + " " + moduleName;
        if (exceedsMaxLength(description)) {
            throw new InvalidArgumentException("Task exceeds maximum description length!");
        }
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Semester semester = selectedInstance.getSemester();
        if (semester == null) {
            throw new SemesterNotSelectedException("OOPS!! Please select a semester!");
        }
        Module module = new Module(moduleCode, moduleName);
        semester.addModule(module);
        ui.printModuleAddedMessage(module);
        selectedInstance.selectModule(module);
        storageManager.writeSemesterList(semesterList);
    }

    /**
     * Checks if description and module code exceeds the maximum description length.
     *
     * @return True if maximum description length is exceeded, false otherwise.
     */
    @Override
    boolean exceedsMaxLength(String description) {
        return description.length() >= DESCRIPTION_LENGTH_MAX;
    }
}
