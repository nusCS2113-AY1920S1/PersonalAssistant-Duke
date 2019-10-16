package duke.command;

import duke.exception.DukeException;
import duke.parser.Convert;
import duke.recipebook.dishlist;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

import java.util.Date;

/**
 * One of the B-Extensions.
 * @author saradj
 */
public class Snooze extends Command {

    private int taskNb;
    private String until;
    private Date date;

    /**
     * The constructor method for snooze.
     *
     * @param taskNb task number
     * @param until snooze until when
     */
    public Snooze(int taskNb, String until) {
        this.taskNb = taskNb;
        this.until = until;
        this.date = Convert.stringToDate(until);
    }

    @Override
    public void execute(dishlist dish1, TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (taskNb < taskList.size() && taskNb >= 0) {
            if (taskList.getTask(taskNb).isDone()) {
                throw new DukeException("Seems like you've already finished that task, no need to snooze it now");
            }
            taskList.changeTaskDate(taskNb, until);
            ui.showChangedDate(Convert.getDateString(date, until),taskList.getTask(taskNb).toString());
            storage.changeContent(taskNb);
        } else {
            throw new DukeException("Enter a valid task number after snooze, between 1 and " + taskList.size());
        }
    }
}

