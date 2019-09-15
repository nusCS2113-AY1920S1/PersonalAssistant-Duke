package duke.command;

import duke.core.DukeException;
import duke.core.Storage;
import duke.core.TaskList;
import duke.core.Ui;
import duke.task.Task;
import duke.task.Todo;

/**
 * Represents a command to reschedule a task by updating its date/time.
 */
public class RescheduleCommand extends Command {

    private int taskId;
    private String newDateTime;


    /**
     * Constructs a RescheduleCommand object.
     *
     * @param taskId  The index of the task to be updated
     * @param newDateTime The new time of which the task should be updated to, expected to be in the format of dd//MM/yyyy HHmm
     */
    public RescheduleCommand(int taskId, String newDateTime) {
        super();
        this.taskId = taskId;
        this.newDateTime = newDateTime;
    }

    /**
     * run the command with the respect TaskList, UI, and storage.
     *
     * @param taskList   The task list where tasks are saved.
     * @param ui      The user interface.
     * @param storage object that handles local text file update
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        try{
            Task task = taskList.getTask(taskId);
            if (task instanceof Todo){
                throw new DukeException("Todo task cannot be reschedule");
            }
            task.updateLocalDateTime(newDateTime);
            storage.save(taskList.fullTaskList());
            ui.taskRescheduled(task);
        }catch(DukeException dukeException){
            throw new DukeException("Fails to reschedule task. " + dukeException.getMessage());
        }
    }

    /**
     * Indicates whether Duke should exist
     *
     * @return A boolean. True if the command tells Duke to exit, false
     * otherwise.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
