package duke.commands;

import duke.commons.DukeException;
import duke.commons.MessageUtil;
import duke.storage.Storage;
import duke.data.tasks.Task;
import duke.data.tasks.TaskWithDates;
import duke.ui.Ui;

import java.time.LocalDateTime;

public class RescheduleCommand extends Command {
    private int index;
    private LocalDateTime newDate;

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
     * @param ui The user interface displaying events on the task list.
     * @param storage The duke.storage object containing task list.
     */
    @Override
    public void execute(Ui ui, Storage storage) throws DukeException {
        try {
            Task task =  storage.getTasks().get(index);
            if (task instanceof TaskWithDates) {
                ((TaskWithDates) task).setStartDate(newDate);
            } else {
                throw new DukeException(MessageUtil.TASK_NOT_FOUND);
            }
            ui.showUpdateTask(task);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(MessageUtil.OUT_OF_BOUNDS);
        }
        storage.write();
    }
}
