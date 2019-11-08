package oof.logic.command.module;

import oof.logic.command.Command;
import oof.model.university.SelectedInstance;
import oof.ui.Ui;
import oof.commons.exceptions.command.CommandException;
import oof.logic.command.organization.exceptions.EmptyListException;
import oof.model.university.Semester;
import oof.model.university.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

//@@author KahLokKee

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
        if (semester.getModules().isEmpty()) {
            throw new EmptyListException("Module list is empty!");
        } else {
            ui.printModuleList(semester);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
