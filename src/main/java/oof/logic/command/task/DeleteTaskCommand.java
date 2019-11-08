package oof.logic.command.task;

import oof.logic.command.Command;
import oof.ui.Ui;
import oof.commons.exceptions.command.InvalidArgumentException;
import oof.model.university.SemesterList;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

//@@author KahLokKee

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
     * Deletes a task from taskList.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws InvalidArgumentException if user input contains invalid commands.
     */
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws InvalidArgumentException {
        if (!taskList.isIndexValid(this.index)) {
            throw new InvalidArgumentException("OOPS!!! Invalid number!");
        }
        Task task = taskList.getTask(this.index);
        taskList.deleteTask(this.index);
        ui.deleteMessage(task, taskList.getSize());
        storageManager.writeTaskList(taskList);
    }

}
