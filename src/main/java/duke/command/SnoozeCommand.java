package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;

/**
 * Represents a Command to snooze a Task.
 */
public class SnoozeCommand extends Command {

    private String index;

    /**
     * Constructor for SnoozeCommand.
     * @param index Index of Task to be snoozed.
     */
    public SnoozeCommand(String index) {
        super();
        this.index = index;
    }

    /**
     * Replaces the timestamp of an Event or Deadline
     * after checking the validity of the task selected as well as the new
     * Timestamp before performing the replacement.
     *
     * @param arr     Instance of TaskList that stores Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) {
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
                    Task newTask = new Deadline(description, date);
                    arr.deleteTask(num);
                    arr.addTaskToIndex(num, newTask);
                    ui.snoozeMessage(newTask);
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
                    Task newTask = new Event(description, startDate, endDate);
                    arr.deleteTask(num);
                    arr.addTaskToIndex(num, newTask);
                    ui.snoozeMessage(newTask);
                }
            } else {
                throw new DukeException("OOPS!!! Task does not have a timestamp!");
            }
        } catch (Exception e) {
            System.out.println("OOPS!!! Invalid number!");
        }
    }

    /**
     * Checks if ExitCommand is called for Duke to terminate.
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}
