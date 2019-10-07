package oof.command;

import oof.TaskList;
import oof.Ui;
import oof.Storage;
import oof.exception.OofException;
import oof.task.Task;

/**
 * Represents a Command to delete a specific Task.
 */
public class DeleteCommand extends Command {

    private int index;

    /**
     * Constructor for DeleteCommand.
     *
     * @param index Represents the index of the Task to be deleted.
     */
    public DeleteCommand(int index) {
        super();
        this.index = index;
    }

    /**
     * Deletes the specific Task defined by the user after confirming the validity of the Command inputted by the user.
     *
     * @param taskList Instance of TaskList that stores Task objects.
     * @param ui       Instance of Ui that is responsible for visual feedback.
     * @param storage  Instance of Storage that enables the reading and writing of Task
     *                 objects to hard disk.
     * @throws OofException Catches invalid commands given by user.
     */
    public void execute(TaskList taskList, Ui ui, Storage storage) throws OofException {
        if (!taskList.isIndexValid(this.index)) {
            throw new OofException("OOPS!!! Invalid number!");
        }
        Task task = taskList.getTask(this.index);
        taskList.deleteTask(this.index);
        ui.deleteMessage(task, taskList.getSize());
        storage.writeToFile(taskList);
    }

    /**
     * Checks if ExitCommand is called for OOF to terminate.
     *
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}
