package oof.command;

import oof.SelectedInstance;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

public class ViewSelectedSemesterCommand extends Command {

    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws OofException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Semester semester = selectedInstance.getSemester();
        if (semester == null) {
            throw new OofException("OOPS!! No semester selected.");
        }
        ui.printCurrentlySelectedSemester(semester);
    }
}