package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.model.Model;
import duke.model.events.Task;
import duke.model.events.TaskWithDates;

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
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        try {
            Task task =  model.getTasks().get(index);
            if (task instanceof TaskWithDates) {
                ((TaskWithDates) task).setStartDate(newDate);
            } else {
                throw new DukeException(Messages.TASK_NOT_FOUND);
            }
            model.save();
            return new CommandResultText(MESSAGE_UPDATE + task);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(Messages.OUT_OF_BOUNDS);
        }
    }
}
