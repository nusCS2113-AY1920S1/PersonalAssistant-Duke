package oof.command;

import oof.TaskList;
import oof.Ui;
import oof.Storage;
import oof.exception.OofException;
import oof.task.Task;

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
     * @param taskList Instance of TaskList that stores Task objects.
     * @param ui       Instance of Ui that is responsible for visual feedback.
     * @param storage  Instance of Storage that enables the reading and writing of Task
     *                 objects to hard disk.
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        try {
            if (!taskList.isIndexValid(this.index)) {
                throw new OofException("OOPS!!! The index is invalid.");
            } else if (taskList.getTask(this.index).getStatus()) {
                throw new OofException("OOPS!!! The task is already marked as done.");
            } else {
                Task task = taskList.getTask(this.index);
                task.setStatus();
                ui.completeMessage(task);
                storage.writeToFile(taskList);
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
