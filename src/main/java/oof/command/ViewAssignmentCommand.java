package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

/**
 * Represents a Command to view the list of Assignments.
 */
public class ViewAssignmentCommand extends Command {

    /**
     * Default Constructor for ViewAssignmentCommand.
     */
    public ViewAssignmentCommand() {
        super();
    }

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        viewAssignmentList(semesterList, ui);
    }

    /**
     * Prints the list of Assignments in a selected Module.
     *
     * @param semesterList Instance of SemesterList Object containing list of Semesters.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @throws OofException if assignments list is empty.
     */
    private void viewAssignmentList(SemesterList semesterList, Ui ui) throws OofException {
        if (Module.getAssignments().isEmpty()) {
            throw new OofException("Assignments List is empty!");
        } else {
            int semesterWanted = ui.scanSemesterOption(semesterList);
            int moduleWanted = ui.scanModuleOption(semesterList.getSemester(semesterWanted));
            ui.printAssignmentList(Semester.getModule(moduleWanted));
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
