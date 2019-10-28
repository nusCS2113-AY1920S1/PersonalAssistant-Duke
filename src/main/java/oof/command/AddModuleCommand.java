package oof.command;

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
    private Semester semester;

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
        String codeAndName = line.replaceFirst("/code", "").trim();
        if (!hasCode(codeAndName)) {
            throw new OofException("OOPS!! The module needs a code.");
        } else {
            String[] codeAndNameSplit = codeAndName.split("/name");
            if (!hasName(codeAndNameSplit)) {
                throw new OofException("OOPS!! The module needs a name");
            }
        }
        String[] codeAndNameSplit = codeAndName.split("/name");
        String code = codeAndNameSplit[INDEX_CODE].trim();
        String name = codeAndNameSplit[INDEX_NAME].trim();
        Module module = new Module(code, name);
        Semester.addModule(module);
        ui.printModuleAddedMessage(module);
        storage.writeSemesterList(semesterList, semester, module);
    }

    /**
     * Checks if module has a code.
     *
     * @param code processed user input.
     * @return true if code is more than length 0 and is not whitespace
     */
    private boolean hasCode(String code) {
        return code.trim().length() > 0;
    }

    /**
     * Checks if module has a name.
     *
     * @param lineSplit processed user input.
     * @return true if name is more than length 0 and is not whitespace
     */
    private boolean hasName(String[] lineSplit) {
        return lineSplit[INDEX_NAME].trim().length() > 0;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
