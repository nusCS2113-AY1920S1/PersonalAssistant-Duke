package oof.command;

import oof.SelectedInstance;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

public class SelectModuleCommand extends Command {

    private int index;

    public SelectModuleCommand(int index) {
        super();
        this.index = index;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws OofException {
        try {
            SelectedInstance selectedInstance = SelectedInstance.getInstance();
            Semester semester = selectedInstance.getSemester();
            Module module = semester.getModule(index);
            selectedInstance.selectModule(module);
            ui.printSelectModuleMessage(module);
        } catch (IndexOutOfBoundsException e) {
            throw new OofException("OOPS!! The index is out of bounds.");
        } catch (NumberFormatException e) {
            throw new OofException("OOPS!! The index is invalid.");
        }
    }
}
