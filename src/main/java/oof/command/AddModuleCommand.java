package oof.command;

import oof.SelectedInstance;
import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

public class AddModuleCommand extends Command {

    private String line;
    private static final int INDEX_CODE = 0;
    private static final int INDEX_NAME = 1;
    private static final String REGEX_SPLIT = "/name";

    /**
     * Constructor for AddModuleCommand.
     *
     * @param line Command inputted by user for processing.
     */
    public AddModuleCommand(String line) {
        super();
        this.line = line;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        String[] argumentSplit = line.split(REGEX_SPLIT);
        if (!hasCode(argumentSplit)) {
            throw new OofException("OOPS!! The module needs a code.");
        } else if (!hasName(argumentSplit)) {
            throw new OofException("OOPS!! The module needs a name");
        }
        String code = argumentSplit[INDEX_CODE].trim();
        String name = argumentSplit[INDEX_NAME].trim();
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Semester semester = selectedInstance.getSemester();
        if (semester == null) {
            throw new OofException("OOPS!! Please select a semester!");
        }
        Module module = new Module(code, name);
        semester.addModule(module);
        ui.printModuleAddedMessage(module);
        storage.writeSemesters(semesterList);
    }

    /**
     * Checks if module has a code.
     *
     * @param argumentSplit processed user input.
     * @return true if Module code is more than length 0 and is not whitespace.
     */
    private boolean hasCode(String[] argumentSplit) {
        return argumentSplit.length > 0 && argumentSplit[INDEX_CODE].trim().length() > 0;
    }

    /**
     * Checks if module has a name.
     *
     * @param argumentSplit processed user input.
     * @return true if name is more than length 0 and is not whitespace
     */
    private boolean hasName(String[] argumentSplit) {
        return argumentSplit.length > 1 && argumentSplit[INDEX_NAME].trim().length() > 0;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
