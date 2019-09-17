package duke.commands;

import duke.commons.DukeException;
import duke.parsers.Parser;
import duke.storage.Storage;
import duke.tasks.Task;
import duke.tasks.TaskWithDates;
import duke.tasks.UniqueTaskList;
import duke.ui.Ui;

import java.time.LocalDateTime;

/**
 * Class representing a command to fetch tasks by date.
 */
public class FetchCommand extends Command {
    private LocalDateTime date;

    /**
     * Creates a new FindCommand with the given keyword.
     *
     * @param date The date to find.
     */
    public FetchCommand(LocalDateTime date) {
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
        UniqueTaskList tasks = storage.getTasksWithDate();
        UniqueTaskList result = new UniqueTaskList();

        for (Task task: tasks) {
            if (task instanceof TaskWithDates) {
                if (((TaskWithDates) task).getStartDate()
                        .toString().substring(0, ((TaskWithDates) task).getStartDate().toString().indexOf("T"))
                        .equals(date.toString().substring(0, date.toString().indexOf("T")))) {
                    result.add(task);
                }
            }
        }
        ui.setResponse(ui.getList(result));
    }
}
