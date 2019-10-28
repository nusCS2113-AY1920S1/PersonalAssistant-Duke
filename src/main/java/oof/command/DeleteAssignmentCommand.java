package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Module;
import oof.model.module.SemesterList;
import oof.model.task.Assignment;
import oof.model.task.TaskList;

/**
 * Represents a Command to delete a specific Assignment.
 */
public class DeleteAssignmentCommand extends Command {
    private int index;
    private static final int EMPTY = 0;

    /**
     * Constructor for DeleteAssignmentCommand.
     *
     * @param index Represents the index of the Assignment to be deleted.
     */
    public DeleteAssignmentCommand(int index) {
        super();
        this.index = index;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        if (Module.getAssignments().size() == EMPTY) {
            throw new OofException("OOPS!!! Invalid number!");
        }
        Assignment assignment = Module.getAssignment(this.index);
        tasks.deleteTask(this.index);
        ui.printAssignmentRemovalMessage(assignment);
        storage.writeTaskList(tasks);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
