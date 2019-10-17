package duke.commands;

import duke.commands.results.CommandResultText;
import duke.commons.exceptions.DukeException;
import duke.model.Model;
import duke.model.TaskList;
import duke.model.events.Task;
import duke.model.events.TaskWithDates;

import javafx.collections.transformation.SortedList;

import java.time.LocalDate;

public class ReminderCommand extends Command {
    private TaskList expiredTask;
    private TaskList upcomingTask;

    /**
     * Creates a new ReminderCommand.
     */
    public ReminderCommand() {
        expiredTask = new TaskList();
        upcomingTask = new TaskList();
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        SortedList<Task> tasks = model.getTasks().getChronoList();
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
        return new CommandResultText(result.toString());
    }
}
