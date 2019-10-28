package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

/**
 * Represents a Command to delete a specific Module.
 */
public class DeleteModuleCommand extends Command {

    private int index;
    private static final int EMPTY = 0;
    private Semester semester;

    /**
     * Constructor for DeleteModuleCommand.
     *
     * @param index Represents the index of the Module to be deleted.
     */
    public DeleteModuleCommand(int index) {
        super();
        this.index = index;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        if (Semester.getModules().size() == EMPTY) {
            throw new OofException("OOPS!!! Invalid number!");
        }
        Module module = Semester.getModule(this.index);
        Semester.removeModule(this.index);
        ui.printModuleRemovalMessage(module);
        storage.writeSemesterList(semesterList, semester, module);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
