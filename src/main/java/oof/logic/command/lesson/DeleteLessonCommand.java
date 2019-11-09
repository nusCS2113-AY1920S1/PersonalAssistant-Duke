package oof.logic.command.lesson;

import oof.logic.command.Command;
import oof.model.university.SelectedInstance;
import oof.ui.Ui;
import oof.commons.exceptions.command.InvalidArgumentException;
import oof.model.university.Lesson;
import oof.model.university.Module;
import oof.model.university.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

//@@author KahLokKee

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
     * @throws InvalidArgumentException if user input contains invalid commands.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, StorageManager storageManager)
            throws InvalidArgumentException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Module module = selectedInstance.getModule();
        if (!module.isLessonIndexValid(index)) {
            throw new InvalidArgumentException("OOPS!!! Invalid number!");
        }
        Lesson lesson = module.getLesson(this.index);
        module.deleteLesson(this.index);
        ui.printLessonRemovalMessage(lesson);
        storageManager.writeSemesterList(semesterList);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
