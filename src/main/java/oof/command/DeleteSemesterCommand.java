package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

/**
 * Represents a Command to delete a specific Semester.
 */
public class DeleteSemesterCommand extends Command {

    private int index;
    private static final int EMPTY = 0;
    private Module module;

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
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        if (semesterList.getSize() == EMPTY) {
            throw new OofException("OOPS!!! Invalid number!");
        }
        Semester semester = semesterList.getSemester(this.index);
        semesterList.removeSemester(this.index);
        ui.printSemesterRemovalMessage(semester);
        storage.writeSemesterList(semesterList, semester, module);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
