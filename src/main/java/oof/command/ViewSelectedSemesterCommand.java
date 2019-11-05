package oof.command;

import oof.SelectedInstance;
import oof.Ui;
import oof.exception.command.SemesterNotSelectedException;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

//@@author KahLokKee

/**
 * Represents a class to view selected semester.
 */
public class ViewSelectedSemesterCommand extends Command {

    /**
     * Retrieves and prints selected semester.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws SemesterNotSelectedException if semester instance is not selected.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws SemesterNotSelectedException {
        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Semester semester = selectedInstance.getSemester();
        if (semester == null) {
            throw new SemesterNotSelectedException("OOPS!! No semester selected.");
        }
        ui.printCurrentlySelectedSemester(semester);
    }
}