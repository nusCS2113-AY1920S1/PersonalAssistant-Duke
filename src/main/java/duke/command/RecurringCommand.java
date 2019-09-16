package duke.command;

import duke.core.DukeException;
import duke.core.Storage;
import duke.core.TaskList;
import duke.core.Ui;
import duke.task.*;

public class RecurringCommand extends Command {

    private int taskIndex;

    public RecurringCommand(int taskIndex) {
        super();
        this.taskIndex = taskIndex;

    }

    @Override
    public boolean isExit() { return false; }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        try {
            Task recurringTask = tasks.getTask(taskIndex);
            if (recurringTask.getDateTime() != null) {
                recurringTask.makeTaskRecurring();
                ui.makeRecurring(recurringTask);
                storage.save(tasks.fullTaskList());
            } else {
                throw new DukeException("Your task needs an initial time in order for it to be recurring.");
            }
        } catch (DukeException e) {
            throw new DukeException("Couldn't make task recurring." + e);
        }

    }
}
