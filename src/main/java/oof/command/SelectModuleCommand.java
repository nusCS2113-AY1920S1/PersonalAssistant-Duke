package oof.command;

import oof.SelectedInstance;
import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

public class SelectModuleCommand extends Command {

    private String line;

    public SelectModuleCommand(String line) {
        super();
        this.line = line;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, Storage storage) throws OofException {
        try {
            int index = Integer.parseInt(line) - 1;
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
