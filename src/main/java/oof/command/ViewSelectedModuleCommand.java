package oof.command;

import oof.SelectedInstance;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Module;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

public class ViewSelectedModuleCommand extends Command {

    public ViewSelectedModuleCommand() {
    }

    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws OofException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Module module = selectedInstance.getModule();
        if (module == null) {
            throw new OofException("OOPS!! No module selected.");
        }
        ui.printCurrentlySelectedModule(module);
    }
}
