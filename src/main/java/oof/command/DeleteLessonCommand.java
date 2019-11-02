package oof.command;

import oof.SelectedInstance;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Lesson;
import oof.model.module.Module;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Represents a Command to delete a specific Lesson.
 */
public class DeleteLessonCommand extends Command {

    private int index;

    /**
     * Constructor for DeleteLessonCommand.
     *
     * @param index Represents the index of the Lesson to be deleted.
     */
    public DeleteLessonCommand(int index) {
        super();
        this.index = index;
    }

    /**
     * Deletes a lesson from module.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param tasks          Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws OofException if user input invalid commands.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, StorageManager storageManager)
            throws OofException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Module module = selectedInstance.getModule();
        if (!module.isLessonIndexValid(index)) {
            throw new OofException("OOPS!!! Invalid number!");
        }
        Lesson lesson = module.getLesson(this.index);
        module.removeLesson(this.index);
        ui.printLessonRemovalMessage(module.getModuleCode(), lesson);
        storageManager.writeSemesterList(semesterList);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
