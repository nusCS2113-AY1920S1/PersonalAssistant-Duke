package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.storage.Storage;
import duke.data.tasks.Task;
import duke.data.tasks.TaskWithDates;

import java.time.LocalDateTime;

public class RescheduleCommand extends Command {
    private int index;
    private LocalDateTime newDate;
    private static final String MESSAGE_UPDATE = "No problem! I've rescheduled this task:\n  ";

    /**
     * Creates a new RescheduleCommand with the given index and newDate of the task.
     *
     * @param index The index of the task.
     */
    public RescheduleCommand(int index, LocalDateTime newDate) {
        this.index = index;
        this.newDate = newDate;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param storage The storage object containing task list.
     */
    @Override
    public CommandResult execute(Storage storage) throws DukeException {
        try {
            Task task =  storage.getTasks().get(index);
            if (task instanceof TaskWithDates) {
                ((TaskWithDates) task).setStartDate(newDate);
            } else {
                throw new DukeException(Messages.TASK_NOT_FOUND);
            }
            storage.write();
            return new CommandResult(MESSAGE_UPDATE + task);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(Messages.OUT_OF_BOUNDS);
        }
    }
}
