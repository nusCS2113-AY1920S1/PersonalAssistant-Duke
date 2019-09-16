package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;

/**
 * Represents a <code>Command</code> to snooze a <code>Task</code>.
 */
public class SnoozeCommand extends Command {

    String index;

    /**
     * Constructor for <code>SnoozeCommand</code>.
     *
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
     *
     * @param arr     Instance of <code>TaskList</code> that stores <code>Task</code> objects.
     * @param ui      Instance of <code>Ui</code> that is responsible for visual feedback.
     * @param storage Instance of <code>Storage</code> that enables the reading and writing of <code>Task</code>
     *                *         objects to harddisk.
     * @throws DukeException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws DukeException {
        try {
            int num = Integer.parseInt(index) - 1;
            if (num >= arr.getSize() || num < 0) {
                throw new DukeException("\u2639 OOPS!!! Invalid number!"); //u2639 is a sad face emoticon
            } else if (storage.taskHasTimestamp(num).equals("deadline")) {
                Task task = arr.getTask(num);
                String description = task.getLine();
                String date = ui.getTimeStamp();
                date = parseTimeStamp(date);
                if (date.equals("failed")) {
                    return;
                } else {
                    Task newtask = new Deadline(description, date);
                    arr.deleteTask(num);
                    arr.addTaskToIndex(num, newtask);
                    ui.snoozeMessage(newtask);
                }
            } else if (storage.taskHasTimestamp(num).equals("event")) {
                Task task = arr.getTask(num);
                String description = task.getLine();
                String startDate = ui.getTimeStamp();
                String endDate = ui.getTimeStamp();
                startDate = parseTimeStamp(startDate);
                endDate = parseTimeStamp(endDate);
                if (startDate.equals("failed") || endDate.equals("failed")) {
                    return;
                } else {
                    Task newtask = new Event(description, startDate, endDate);
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
     *
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}
