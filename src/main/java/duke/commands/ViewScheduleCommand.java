package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import duke.data.tasks.Task;
import duke.data.tasks.TaskWithDates;
import duke.data.UniqueTaskList;

import javafx.collections.transformation.SortedList;

import java.time.LocalDateTime;

public class ViewScheduleCommand extends Command {
    private LocalDateTime date;
    private static final String MESSAGE_SHOW_CALENDAR = "Calendar is launching...";

    /**
     * Creates a new ViewScheduleCommand with the given date.
     *
     * @param date The date to find.
     */
    public ViewScheduleCommand(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param storage The storage object containing task list.
     */
    @Override
    public CommandResult execute(Storage storage) throws DukeException {
        SortedList<Task> tasks = storage.getTasks().getChronoList();
        UniqueTaskList result = new UniqueTaskList();

        for (Task task : tasks) {
            if (((TaskWithDates) task).getStartDate() != null
                    && ((TaskWithDates) task).getStartDate().toString().substring(0,
                        ((TaskWithDates) task).getStartDate().toString().indexOf("T"))
                        .equals(date.toString().substring(0, date.toString().indexOf("T")))) {
                result.add(task);
            }
        }
        CommandResult commandResult = new CommandResult(MESSAGE_SHOW_CALENDAR);
        commandResult.setCalendar(true);
        commandResult.setTasks(storage.getTasks());
        return commandResult;
    }
}
