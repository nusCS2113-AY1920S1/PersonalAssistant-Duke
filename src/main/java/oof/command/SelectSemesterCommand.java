package oof.command;

import oof.SelectedInstance;
import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

public class SelectSemesterCommand extends Command {

    private String line;

    public SelectSemesterCommand(String line) {
        super();
        this.line = line;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, Storage storage) throws OofException {
        try {
            int index = Integer.parseInt(line) - 1;
            SelectedInstance selectedInstance = SelectedInstance.getInstance();
            Semester semester = semesterList.getSemester(index);
            selectedInstance.selectSemester(semester);
            ui.printSelectSemesterMessage(semester);
        } catch (IndexOutOfBoundsException e) {
            throw new OofException("OOPS!! The index is out of bounds.");
        } catch (NumberFormatException e) {
            throw new OofException("OOPS!! The index is invalid.");
        }
    }
}
