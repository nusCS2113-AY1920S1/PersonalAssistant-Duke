package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

/**
 * Represents a Command to view the list of Lessons.
 */
public class ViewLessonCommand extends Command {

    /**
     * Default Constructor for ViewLessonCommand.
     */
    public ViewLessonCommand() {
        super();
    }

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        viewLessonList(semesterList, ui);
    }

    /**
     * Prints the list of Lessons in a selected Module.
     *
     * @param semesterList Instance of SemesterList Object containing list of Semesters.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @throws OofException if lessons list is empty.
     */
    private void viewLessonList(SemesterList semesterList, Ui ui) throws OofException {
        if (Module.getLessons().isEmpty()) {
            throw new OofException("Lessons List is empty!");
        } else {
            int semesterWanted = ui.scanSemesterOption(semesterList);
            int moduleWanted = ui.scanModuleOption(semesterList.getSemester(semesterWanted));
            ui.printLessonList(Semester.getModule(moduleWanted));
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
