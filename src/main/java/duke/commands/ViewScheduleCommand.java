package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.storage.Storage;
import duke.data.tasks.Task;
import duke.data.tasks.TaskWithDates;
import duke.data.UniqueTaskList;
import duke.ui.Ui;
import javafx.collections.transformation.SortedList;

import java.time.LocalDateTime;

public class ViewScheduleCommand extends Command {
    private LocalDateTime date;

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
     * @param ui The user interface displaying events on the task list.
     * @param storage The duke.storage object containing task list.
     */
    @Override
    public void execute(Ui ui, Storage storage) throws DukeException {
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
        ui.showList(result);
        ui.showCalendar(storage.getTasks());
    }
}
