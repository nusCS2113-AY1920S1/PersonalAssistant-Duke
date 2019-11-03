package oof.command;

import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

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

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, StorageManager storageManager)
            throws OofException {
        if (!semesterList.isIndexValid(index)) {
            throw new OofException("OOPS!!! Invalid number!");
        }
        Semester semester = semesterList.getSemester(this.index);
        semesterList.removeSemester(this.index);
        ui.printSemesterRemovalMessage(semester);
        storageManager.writeSemesterList(semesterList);
    }
}
