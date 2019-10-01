package oof.command;

import oof.Storage;
import oof.TaskList;
import oof.Ui;
import oof.exception.OofException;
import oof.task.Deadline;
import oof.task.Event;
import oof.task.Task;

/**
 * Represents a Command to snooze a Task.
 */
public class SnoozeCommand extends Command {

    private String index;

    /**
     * Constructor for SnoozeCommand.
     *
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
            if (!isIndexValid(arr, num)) {
                throw new OofException("\u2639 OOPS!!! Invalid number!"); //u2639 is a sad face emoticon
            } else if (isDeadline(arr, num)) {
                Task task = arr.getTask(num);
                String description = task.getLine();
                String date = ui.getTimeStamp();
                date = parseTimeStamp(date);
                if (isDateValid(date)) {
                    Task newTask = new Deadline(description, date);
                    arr.deleteTask(num);
                    arr.addTaskToIndex(num, newTask);
                    ui.printSnoozeMessage(newTask);
                } else {
                    throw new OofException("Timestamp given is invalid! Please try again.");
                }
            } else if (isEvent(arr, num)) {
                Task task = arr.getTask(num);
                String description = task.getLine();
                String startDate = ui.getTimeStamp();
                String endDate = ui.getTimeStamp();
                startDate = parseTimeStamp(startDate);
                endDate = parseTimeStamp(endDate);
                if (isDateValid(startDate) && isDateValid(endDate)) {
                    Task newTask = new Event(description, startDate, endDate);
                    arr.deleteTask(num);
                    arr.addTaskToIndex(num, newTask);
                    ui.printSnoozeMessage(newTask);
                } else {
                    throw new OofException("Timestamp given is invalid! Please try again.");
                }
            } else {
                throw new OofException("OOPS!!! Task does not have a timestamp!");
            }
        } catch (OofException e) {
            ui.printOofException(e);
        }
    }

    private boolean isIndexValid(TaskList arr, int num) {
        return num < arr.getSize() && num >= 0;
    }

    private boolean isDeadline(TaskList arr, int num) {
        return arr.getTask(num) instanceof Deadline;
    }

    private boolean isDateValid(String date) {
        return !date.equals("failed");
    }

    private boolean isEvent(TaskList arr, int num) {
        return arr.getTask(num) instanceof Event;
    }

    /**
     * Checks if ExitCommand is called for Oof to terminate.
     *
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}
