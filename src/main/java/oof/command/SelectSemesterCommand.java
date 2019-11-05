package oof.command;

import oof.SelectedInstance;
import oof.Ui;
import oof.exception.CommandException.InvalidArgumentException;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Command to select a semester from semesterList.
 */
public class SelectSemesterCommand extends Command {

    private int index;

    /**
     * Constructor for SelectSemesterCommand.
     *
     * @param index Semester index in the semesterList.
     */
    public SelectSemesterCommand(int index) {
        super();
        this.index = index;
    }

    /**
     * Selects a semester from semesterList.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws InvalidArgumentException if user input invalid commands.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws InvalidArgumentException {
        try {
            SelectedInstance selectedInstance = SelectedInstance.getInstance();
            Semester semester = semesterList.getSemester(index);
            selectedInstance.selectSemester(semester);
            ui.printSelectSemesterMessage(semester);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidArgumentException("OOPS!! The index is out of bounds.");
        }
    }
}