package oof.command;

import oof.Ui;
import oof.exception.command.CommandException;
import oof.exception.command.EmptyListException;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

//@@author KahLokKee

/**
 * Represents a Command to view the list of Semesters.
 */
public class ViewAllSemesterCommand extends Command {

    /**
     * Default Constructor for ViewSemesterCommand.
     */
    public ViewAllSemesterCommand() {
        super();
    }

    /**
     * Opens menu to view, add, edit or remove Semesters.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storageManager      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws EmptyListException if semester list or module list is empty.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, StorageManager storageManager)
            throws CommandException {
        if (semesterList.getSemesterList().isEmpty()) {
            throw new EmptyListException("Semester List is empty!");
        } else {
            ui.printSemesterList(semesterList);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
