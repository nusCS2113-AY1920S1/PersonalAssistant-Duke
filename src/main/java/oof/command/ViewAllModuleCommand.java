package oof.command;

import oof.SelectedInstance;
import oof.Ui;
import oof.exception.CommandException.CommandException;
import oof.exception.CommandException.EmptyListException;
import oof.exception.CommandException.SemesterNotSelectedException;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Represents a Command to view the list of Modules.
 */
public class ViewAllModuleCommand extends Command {

    /**
     * Default Constructor for SemesterCommand.
     */
    public ViewAllModuleCommand() {
        super();
    }

    /**
     * Prints the list of Modules in a selected Semester.
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores oof.model.task.Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws CommandException if Semester intance is not selected or module list is empty.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws CommandException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Semester semester = selectedInstance.getSemester();
        if (semester == null) {
            throw new SemesterNotSelectedException("OOPS!! No Semester selected.");
        } else if (semester.getModules().isEmpty()) {
            throw new EmptyListException("Module List is empty!");
        } else {
            ui.printModuleList(semester);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
