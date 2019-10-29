package oof.command;

import oof.SelectedInstance;
import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

public class ViewSelectedSemesterCommand extends Command {

    public ViewSelectedSemesterCommand() {
    }

    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, Storage storage) throws OofException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Semester semester = selectedInstance.getSemester();
        if (semester == null) {
            throw new OofException("OOPS!! No semester selected.");
        }
        ui.printCurrentlySelectedSemester(semester);
    }
}