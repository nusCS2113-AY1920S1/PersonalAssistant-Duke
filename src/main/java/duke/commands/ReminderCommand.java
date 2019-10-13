package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import duke.data.tasks.Task;
import duke.data.tasks.TaskWithDates;
import duke.data.UniqueTaskList;

import javafx.collections.transformation.SortedList;

import java.time.LocalDate;

public class ReminderCommand extends Command {
    private UniqueTaskList expiredTask;
    private UniqueTaskList upcomingTask;

    /**
     * Creates a new ReminderCommand.
     */
    public ReminderCommand() {
        expiredTask = new UniqueTaskList();
        upcomingTask = new UniqueTaskList();
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param storage The storage object containing task list.
     */
    @Override
    public CommandResult execute(Storage storage) throws DukeException {
        SortedList<Task> tasks = storage.getTasks().getChronoList();
        for (Task t : tasks) {
            if (t.isDone()) {
                continue;
            }
            LocalDate date = ((TaskWithDates) t).getStartDate().toLocalDate();
            LocalDate now = LocalDate.now();
            if (date.compareTo(now) < 0) {
                expiredTask.add(t);
            } else {
                upcomingTask.add(t);
            }
        }
        StringBuilder result = new StringBuilder("Here are the tasks that are expired:\n");
        for (Task t : expiredTask) {
            result.append(t.toString());
        }
        result.append("here are the tasks that are upcoming:\n");
        for (Task t : upcomingTask) {
            result.append(t.toString());
        }
        return new CommandResult(result.toString());
    }
}
