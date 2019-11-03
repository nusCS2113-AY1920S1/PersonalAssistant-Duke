package oof.command;

import oof.SelectedInstance;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

public class SelectSemesterCommand extends Command {

    private int index;

    public SelectSemesterCommand(int index) {
        super();
        this.index = index;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws OofException {
        try {
            SelectedInstance selectedInstance = SelectedInstance.getInstance();
            Semester semester = semesterList.getSemester(index);
            selectedInstance.selectSemester(semester);
            ui.printSelectSemesterMessage(semester);
        } catch (IndexOutOfBoundsException e) {
            throw new OofException("OOPS!! The index is out of bounds.");
        }
    }
}