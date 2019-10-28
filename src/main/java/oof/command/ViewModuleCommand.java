package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

/**
 * Represents a Command to view the list of Modules.
 */
public class ViewModuleCommand extends Command {

    /**
     * Default Constructor for SemesterCommand.
     */
    public ViewModuleCommand() {
        super();
    }

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        viewModuleList(semesterList, ui);
    }

    /**
     * Prints the list of Modules in a selected Semester.
     *
     * @param semesterList Instance of SemesterList Object containing list of Semesters.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @throws OofException if modules list is empty.
     */
    private void viewModuleList(SemesterList semesterList, Ui ui) throws OofException {
        if (Semester.getModules().isEmpty()) {
            throw new OofException("Module List is empty!");
        } else {
            int semesterWanted = ui.scanSemesterOption(semesterList);
            ui.printModuleList(semesterList.getSemester(semesterWanted));
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
