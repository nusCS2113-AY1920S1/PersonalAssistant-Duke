package duke.command;

import duke.core.DukeException;
import duke.core.Storage;
import duke.core.TaskList;
import duke.core.Ui;
import duke.task.*;

import java.time.LocalDateTime;

public class RecurringCommand extends Command {

    /**
     * Used to identify the task being marked as recurring.
     */
    private int taskIndex;
    protected Task.RecurringFrequency frequency;

    public RecurringCommand(int taskIndex, Task.RecurringFrequency frequency) {
        super();
        this.taskIndex = taskIndex;
        this.frequency = frequency;
    }

    /**
     * Indicates whether Duke should exist
     *
     * @return A boolean. True if the command tells Duke to exit, false
     * otherwise.
     */
    @Override
    public boolean isExit() { return false; }

    /**
     * run the command with the respect TaskList, UI, and storage.
     *
     * Checks whether there is a DateTime for the listed task, and if so,
     * marks it as 'recurring', calls the Ui to print a certain output, and informs
     * storage that the task is now recurring.
     *
     * @param tasks   The task list where tasks are saved.
     * @param ui      The user interface.
     * @param storage object that handles local text file update
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        try {
            Task recurringTask = tasks.getTask(taskIndex);
            if (recurringTask.getDateTime() != null) {
                if (!recurringTask.isTaskRecurring()) {
                    recurringTask.makeTaskRecurring(this.frequency);
                    ui.makeRecurring(recurringTask);
                }
                recurringTask.recurringTaskTimeUpdate();
                storage.save(tasks.fullTaskList());
            } else {
                recurringTask.updateLocalDateTime(LocalDateTime.now().toString());
            }
        } catch (DukeException e) {
            throw new DukeException("I couldn't make the task recurring. " + e);
        }

    }
}
