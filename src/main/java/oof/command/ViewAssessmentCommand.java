package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

/**
 * Represents a Command to view the list of Assessments.
 */
public class ViewAssessmentCommand extends Command {

    /**
     * Default Constructor for ViewAssessmentCommand.
     */
    public ViewAssessmentCommand() {
        super();
    }

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        viewAssessmentList(semesterList, ui);
    }

    /**
     * Prints the list of Assessments in a selected Module.
     *
     * @param semesterList Instance of SemesterList Object containing list of Semesters.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @throws OofException if assessments list is empty.
     */
    private void viewAssessmentList(SemesterList semesterList, Ui ui) throws OofException {
        if (Module.getAssessments().isEmpty()) {
            throw new OofException("Assessments List is empty!");
        } else {
            int semesterWanted = ui.scanSemesterOption(semesterList);
            int moduleWanted = ui.scanModuleOption(semesterList.getSemester(semesterWanted));
            ui.printAssessmentList(Semester.getModule(moduleWanted));
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
