package oof.logic.command.lesson;

import oof.logic.command.Command;
import oof.model.university.SelectedInstance;
import oof.ui.Ui;
import oof.commons.exceptions.command.CommandException;
import oof.logic.command.organization.exceptions.EmptyListException;
import oof.model.university.Module;
import oof.model.university.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

//@@author KahLokKee

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

    /**
     * Retrieves and prints list of lessons.
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws CommandException if module instance is not selected or if lessons list is empty.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws CommandException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Module module = selectedInstance.getModule();
        if (module.getLessons().isEmpty()) {
            throw new EmptyListException("OOPS!! Lesson list is empty!");
        } else {
            ui.printLessonList(module);
        }

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
