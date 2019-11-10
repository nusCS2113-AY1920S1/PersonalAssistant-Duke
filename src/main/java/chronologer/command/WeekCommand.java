package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.Storage;
import chronologer.task.TaskList;

public class WeekCommand extends Command {
    private int weekNumber;

    public WeekCommand(int weekToStore) {
        this.weekNumber = weekToStore;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        tasks.updateWeek(weekNumber);
        tasks.updateGui(null);
    }
}
