package duke.command;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a <code>Command</code> to snooze a <code>Task</code>.
 */
public class SnoozeCommand extends Command {

    String index;

    /**
     * Constructor for <code>SnoozeCommand</code>.
     * @param index Index of <code>Task</code> to be snoozed.
     */
    public SnoozeCommand(String index) {
        super();
        this.index = index;
    }

    /**
     * Replaces the timestamp of an <code>Event</code> or <code>Deadline</code>
     * after checking the validity of the task selected as well as the new
     * <code>Timestamp</code> before performing the replacement.
     * @param arr Instance of <code>TaskList</code> that stores <code>Task</code> objects.
     * @param ui Instance of <code>Ui</code> that is responsible for visual feedback.
     * @param storage Instance of <code>Storage</code> that enables the reading and writing of <code>Task</code>
     *      *         objects to harddisk.
     * @throws DukeException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws DukeException {
        try {
            int num = Integer.parseInt(index) - 1;
            if (num >= arr.getSize() || num < 0) {
                throw new DukeException("\u2639 OOPS!!! Invalid number!"); //u2639 is a sad face emoticon
            } else if (storage.taskHasTimestamp(num) == "deadline" || storage.taskHasTimestamp(num) == "event") {
                Task task = arr.getTask(num);
                String description = task.getLine();
                String date = ui.getTimeStamp();
                date = parseTimeStamp(date);
                if (date == "failed") {
                    return;
                } else if (storage.taskHasTimestamp(num) == "deadline") {
                    Task newtask = new Deadline(description, date);
                    arr.deleteTask(num);
                    arr.addTaskToIndex(num, newtask);
                    ui.snoozeMessage(newtask);
                } else {
                    Task newtask = new Event(description, date);
                    arr.deleteTask(num);
                    arr.addTaskToIndex(num, newtask);
                    ui.snoozeMessage(newtask);
                }
            } else {
                throw new DukeException("OOPS!!! Task does not have a timestamp!");
            }
        } catch (Exception e) {
            System.out.println("OOPS!!! Invalid number!");
        }
    }

    /**
     * Checks if <code>ExitCommand</code> is called for <code>Duke</code>
     * to terminate.
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}
