package oof.command;

import oof.model.module.SemesterList;
import oof.Ui;
import oof.exception.OofException;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Represents a Command to delete a specific Task.
 */
public class DeleteTaskCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    private int index;

    /**
     * Constructor for DeleteCommand.
     *
     * @param index Represents the index of the Task to be deleted.
     */
    public DeleteTaskCommand(int index) {
        super();
        this.index = index;
    }

    /**
     * Deletes the specific Task defined by the user after confirming the validity of the Command inputted by the user.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param taskList     Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storageManager      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if user input invalid commands.
     */
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws OofException {
        if (!taskList.isIndexValid(this.index)) {
            throw new OofException("OOPS!!! Invalid number!");
        }
        Task task = taskList.getTask(this.index);
        taskList.deleteTask(this.index);
        ui.deleteMessage(task, taskList.getSize());
        storageManager.writeTaskList(taskList);
    }

}
