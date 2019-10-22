package oof.command;

import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.Ui;
import oof.Storage;
import oof.exception.OofException;
import oof.model.task.Task;

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
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if user input invalid commands.
     */
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        if (!tasks.isIndexValid(this.index)) {
            throw new OofException("OOPS!!! Invalid number!");
        }
        Task task = tasks.getTask(this.index);
        tasks.deleteTask(this.index);
        ui.deleteMessage(task, tasks.getSize());
        storage.writeTaskList(tasks);
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
