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
import oof.model.tracker.TrackerList;

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
     * @param taskList     Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     */
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, Storage storage) {
        try {
            if (!isIndexValid(taskList, this.index)) {
                throw new OofException("OOPS!!! Invalid number!");
            }
            Task task = taskList.getTask(this.index);
            if (task instanceof Deadline) {
                snoozeDeadline(task, taskList, ui, storage);
            } else if (task instanceof Event) {
                snoozeEvent(task, taskList, ui, storage);
            } else {
                snoozeTodo(task, taskList, ui, storage);
            }
        } catch (OofException e) {
            ui.printOofException(e);
        }
    }

    /**
     * Snoozes Deadline tasks.
     *
     * @param task     Deadline task to be snoozed.
     * @param taskList Instance of TaskList that stores Task objects.
     * @param ui       Instance of Ui that is responsible for visual feedback.
     * @param storage  Instance of Storage that enables the reading and writing of Task
     * @throws OofException Throws OofException when datetime is invalid.
     */
    private void snoozeDeadline(Task task, TaskList taskList, Ui ui, Storage storage) throws OofException {
        String description = task.getDescription();
        String date = ui.getTimeStamp();
        date = parseTimeStamp(date);
        if (isDateValid(date)) {
            Deadline deadline = new Deadline(description, date);
            taskList.deleteTask(this.index);
            taskList.addTaskToIndex(this.index, deadline);
            ui.printSnoozeMessage(deadline);
            storage.writeTaskList(taskList);
        } else {
            throw new OofException("Timestamp given is invalid! Please try again.");
        }
    }

    /**
     * Snoozes Event tasks.
     *
     * @param task     Event task to be snoozed.
     * @param taskList Instance of TaskList that stores Task objects.
     * @param ui       Instance of Ui that is responsible for visual feedback.
     * @param storage  Instance of Storage that enables the reading and writing of Task
     * @throws OofException Throws OofException when datetime is invalid.
     */
    private void snoozeEvent(Task task, TaskList taskList, Ui ui, Storage storage) throws OofException {
        String description = task.getDescription();
        String startDate = ui.getTimeStamp();
        String endDate = ui.getTimeStamp();
        startDate = parseTimeStamp(startDate);
        endDate = parseTimeStamp(endDate);
        if (isDateValid(startDate) && isDateValid(endDate)) {
            Event event = new Event(description, startDate, endDate);
            taskList.deleteTask(this.index);
            taskList.addTaskToIndex(this.index, event);
            ui.printSnoozeMessage(event);
            storage.writeTaskList(taskList);
        } else {
            throw new OofException("Timestamp given is invalid! Please try again.");
        }
    }

    /**
     * Snoozes Todo tasks.
     *
     * @param task     Todo task to be snoozed.
     * @param taskList Instance of TaskList that stores Task objects.
     * @param ui       Instance of Ui that is responsible for visual feedback.
     * @param storage  Instance of Storage that enables the reading and writing of Task
     * @throws OofException Throws OofException when datetime is invalid.
     */
    private void snoozeTodo(Task task, TaskList taskList, Ui ui, Storage storage) throws OofException {
        String description = task.getDescription();
        String date = ui.getTimeStamp();
        if (isDateValid(date)) {
            Todo todo = new Todo(description, date);
            taskList.deleteTask(this.index);
            taskList.addTaskToIndex(this.index, todo);
            ui.printSnoozeMessage(todo);
            storage.writeTaskList(taskList);
        } else {
            throw new OofException("Timestamp given is invalid! Please try again.");
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
}
