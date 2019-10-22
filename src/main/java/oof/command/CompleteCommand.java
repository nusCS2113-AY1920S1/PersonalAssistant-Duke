package oof.command;

import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.Ui;
import oof.Storage;
import oof.exception.OofException;
import oof.model.task.Task;

/**
 * Represents a Command to mark Task as done.
 */
public class CompleteCommand extends Command {

    private int index;

    /**
     * Constructor for CompleteCommand.
     *
     * @param index Represents the index of the Task to be marked as done.
     */
    public CompleteCommand(int index) {
        super();
        this.index = index;
    }

    /**
     * Marks the specific Task defined by the user as done
     * after confirming the validity of the Command inputted by the user.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     */
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) {
        try {
            if (!tasks.isIndexValid(this.index)) {
                throw new OofException("OOPS!!! The index is invalid.");
            } else if (tasks.getTask(this.index).getStatus()) {
                throw new OofException("OOPS!!! The task is already marked as done.");
            } else {
                Task task = tasks.getTask(this.index);
                task.setStatus();
                ui.completeMessage(task);
                storage.writeTaskList(tasks);
                storage.checkDone(task.toString());
            }
        } catch (OofException e) {
            ui.printOofException(e);
        }
    }

    /**
     * Checks if ExitCommand is called for Oof to terminate.
     *
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}
