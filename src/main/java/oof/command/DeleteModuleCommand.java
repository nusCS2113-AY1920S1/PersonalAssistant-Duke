package oof.command;

import oof.SelectedInstance;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Represents a Command to delete a specific Module.
 */
public class DeleteModuleCommand extends Command {

    private int index;

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
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, StorageManager storageManager)
            throws OofException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Semester semester = selectedInstance.getSemester();
        if (!semester.isIndexValid(index)) {
            throw new OofException("OOPS!!! Invalid number!");
        }
        Module module = semester.getModule(index);
        semester.removeModule(index);
        ui.printModuleRemovalMessage(module);
        storageManager.writeSemesterList(semesterList);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
