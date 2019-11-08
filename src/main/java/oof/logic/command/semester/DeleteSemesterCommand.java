package oof.logic.command.semester;

import oof.logic.command.Command;
import oof.model.university.SelectedInstance;
import oof.ui.Ui;
import oof.commons.exceptions.command.InvalidArgumentException;
import oof.model.university.Semester;
import oof.model.university.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

//@@author KahLokKee

/**
 * Represents a Command to delete a specific Semester.
 */
public class DeleteSemesterCommand extends Command {

    private int index;

    /**
     * Constructor for DeleteSemesterCommand.
     *
     * @param index Represents the index of the Semester to be deleted.
     */
    public DeleteSemesterCommand(int index) {
        super();
        this.index = index;
    }

    /**
     * Deletes semester from semesterList.
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
        if (!semesterList.isIndexValid(index)) {
            throw new InvalidArgumentException("OOPS!!! The index is invalid.");
        }
        Semester semester = semesterList.getSemester(this.index);
        semesterList.deleteSemester(this.index);
        SelectedInstance selectedInstance  = SelectedInstance.getInstance();
        selectedInstance.resetSemester();
        ui.printSemesterRemovalMessage(semester);
        storageManager.writeSemesterList(semesterList);
    }
}
