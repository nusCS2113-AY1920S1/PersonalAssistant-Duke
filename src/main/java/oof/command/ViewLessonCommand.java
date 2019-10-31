package oof.command;

import oof.SelectedInstance;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Module;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

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
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, StorageManager storageManager)
            throws OofException {
        viewLessonList(ui);
    }

    /**
     * Prints the list of Lessons in a selected Module.
     *
     * @param ui Instance of Ui that is responsible for visual feedback.
     * @throws OofException if lessons list is empty.
     */
    private void viewLessonList(Ui ui) throws OofException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Module module = selectedInstance.getModule();
        if (module == null) {
            throw new OofException("OOPS!! No module selected.");
        } else if (module.getLessons().isEmpty()) {
            throw new OofException("OOPS!! Lessons List is empty!");
        } else {
            ui.printLessonList(module);
        }

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
