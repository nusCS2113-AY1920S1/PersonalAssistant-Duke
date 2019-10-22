package oof.command;

import oof.Storage;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.Ui;
import oof.exception.OofException;
import oof.model.task.Deadline;
import oof.model.task.Event;
import oof.model.task.Task;
import oof.model.task.Todo;

/**
 * Represents a Command to snooze a Task.
 */
public class SnoozeCommand extends Command {

    private int index;

    /**
     * Constructor for SnoozeCommand.
     *
     * @param index Index of Task to be snoozed.
     */
    public SnoozeCommand(int index) {
        super();
        this.index = index;
    }

    /**
     * Replaces the timestamp of a Task after checking the validity of the task selected as well as the new
     * timestamp before performing the replacement.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param arr          Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     */
    public void execute(SemesterList semesterList, TaskList arr, Ui ui, Storage storage) {
        try {
            if (!isIndexValid(arr, this.index)) {
                throw new OofException("\u2639 OOPS!!! Invalid number!"); //u2639 is a sad face emoticon
            }
            Task task = arr.getTask(this.index);
            if (task instanceof Deadline) {
                String description = task.getDescription();
                String date = ui.getTimeStamp();
                date = parseTimeStamp(date);
                if (isDateValid(date)) {
                    Deadline deadline = new Deadline(description, date);
                    arr.deleteTask(this.index);
                    arr.addTaskToIndex(this.index, deadline);
                    ui.printSnoozeMessage(deadline);
                    storage.writeTaskList(arr);
                } else {
                    throw new OofException("Timestamp given is invalid! Please try again.");
                }
            } else if (task instanceof Event) {
                String description = task.getDescription();
                String startDate = ui.getTimeStamp();
                String endDate = ui.getTimeStamp();
                startDate = parseTimeStamp(startDate);
                endDate = parseTimeStamp(endDate);
                if (isDateValid(startDate) && isDateValid(endDate)) {
                    Event event = new Event(description, startDate, endDate);
                    arr.deleteTask(this.index);
                    arr.addTaskToIndex(this.index, event);
                    ui.printSnoozeMessage(event);
                    storage.writeTaskList(arr);
                } else {
                    throw new OofException("Timestamp given is invalid! Please try again.");
                }
            } else {
                String description = task.getDescription();
                String date = ui.getTimeStamp();
                if (isDateValid(date)) {
                    Todo todo = new Todo(description, date);
                    arr.deleteTask(this.index);
                    arr.addTaskToIndex(this.index, todo);
                    ui.printSnoozeMessage(todo);
                    storage.writeTaskList(arr);
                } else {
                    throw new OofException("Timestamp given is invalid! Please try again.");
                }
            }
        } catch (OofException e) {
            ui.printOofException(e);
        }
    }

    /**
     * Checks if index is within bounds of TaskList.
     *
     * @param arr   TaskList containing tasks.
     * @param index Index of TaskList.
     * @return True if index is within bounds of TaskList, false otherwise.
     */
    private boolean isIndexValid(TaskList arr, int index) {
        return index < arr.getSize() && index >= 0;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
