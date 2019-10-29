package oof.command;

import oof.SelectedInstance;
import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Module;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

public class ViewSelectedModuleCommand extends Command {

    public ViewSelectedModuleCommand() {
    }

    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, Storage storage) throws OofException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Module module = selectedInstance.getModule();
        if (module == null) {
            throw new OofException("OOPS!! No module selected.");
        }
        ui.printCurrentlySelectedModule(module);
    }
}
