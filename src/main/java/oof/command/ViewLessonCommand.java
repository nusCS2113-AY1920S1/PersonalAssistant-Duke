package oof.command;

import oof.SelectedInstance;
import oof.Ui;
import oof.exception.command.CommandException;
import oof.exception.command.EmptyListException;
import oof.exception.command.ModuleNotSelectedException;
import oof.model.module.Module;
import oof.model.module.SemesterList;
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
        if (module == null) {
            throw new ModuleNotSelectedException("OOPS!! No module selected.");
        } else if (module.getLessons().isEmpty()) {
            throw new EmptyListException("OOPS!! Lessons List is empty!");
        } else {
            ui.printLessonList(module);
        }

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
