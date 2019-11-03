package oof.command;

import oof.SelectedInstance;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Represents a Command to view the list of Modules.
 */
public class ViewAllModuleCommand extends Command {

    /**
     * Default Constructor for SemesterCommand.
     */
    public ViewAllModuleCommand() {
        super();
    }

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, StorageManager storageManager)
            throws OofException {
        viewModuleList(ui);
    }

    /**
     * Prints the list of Modules in a selected Semester.
     *
     * @param ui Instance of Ui that is responsible for visual feedback.
     * @throws OofException if modules list is empty.
     */
    private void viewModuleList(Ui ui) throws OofException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Semester semester = selectedInstance.getSemester();
        if (semester == null) {
            throw new OofException("OOPS!! No Semester selected.");
        } else if (semester.getModules().isEmpty()) {
            throw new OofException("Module List is empty!");
        } else {
            ui.printModuleList(semester);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
