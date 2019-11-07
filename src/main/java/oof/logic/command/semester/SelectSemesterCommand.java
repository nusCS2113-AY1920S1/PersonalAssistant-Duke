package oof.logic.command.semester;

import oof.logic.command.Command;
import oof.model.semester.SelectedInstance;
import oof.ui.Ui;
import oof.commons.exceptions.command.InvalidArgumentException;
import oof.model.semester.Semester;
import oof.model.semester.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

//@@author KahLokKee

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