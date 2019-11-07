package oof.logic.command.organization;

import oof.commons.exceptions.command.CommandException;
import oof.commons.exceptions.command.InvalidArgumentException;
import oof.logic.command.organization.exceptions.TaskAlreadyCompletedException;
import oof.logic.command.Command;
import oof.model.semester.SemesterList;
import oof.ui.Ui;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

/**
 * Represents a Command to mark Task as done.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";
    private int index;

    /**
     * Constructor for CompleteCommand.
     *
     * @param index Represents the index of the Task to be marked as done.
     */
    public DoneCommand(int index) {
        super();
        this.index = index;
    }

    /**
     * Marks a task as done.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     * @throws CommandException if task is already completed or index argument is invalid.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager)
            throws CommandException {
        if (!taskList.isIndexValid(this.index)) {
            throw new InvalidArgumentException("OOPS!!! The index is invalid.");
        } else if (taskList.getTask(this.index).getStatus()) {
            throw new TaskAlreadyCompletedException("OOPS!!! The task is already marked as done.");
        } else {
            Task task = taskList.getTask(this.index);
            task.setStatus();
            ui.completeMessage(task);
            storageManager.writeTaskList(taskList);
        }
    }

}
