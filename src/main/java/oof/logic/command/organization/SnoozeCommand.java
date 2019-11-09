package oof.logic.command.organization;

import oof.logic.command.Command;
import oof.ui.Ui;
import oof.commons.exceptions.command.CommandException;
import oof.commons.exceptions.command.InvalidArgumentException;
import oof.model.university.SemesterList;
import oof.model.task.Deadline;
import oof.model.task.Event;
import oof.model.task.Task;
import oof.model.task.TaskList;
import oof.model.task.Todo;
import oof.storage.StorageManager;

//@@author jasperosy

/**
 * Represents a Command to snooze a Task.
 */
public class SnoozeCommand extends Command {

    public static final String COMMAND_WORD = "snooze";
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
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     * @throws InvalidArgumentException if task index or datetime arguments are invalid.
     */
    public void execute(SemesterList semesterList, TaskList taskList, Ui ui, StorageManager storageManager) throws
            CommandException {
        if (!isIndexValid(taskList, this.index)) {
            throw new InvalidArgumentException("OOPS!!! Invalid number!");
        }
        Task task = taskList.getTask(this.index);
        if (task instanceof Deadline) {
            snoozeDeadline(task, taskList, ui, storageManager);
        } else if (task instanceof Event) {
            snoozeEvent(task, taskList, ui, storageManager);
        } else {
            snoozeTodo(task, taskList, ui, storageManager);
        }
    }

    /**
     * Snoozes Deadline tasks.
     *
     * @param task           Deadline task to be snoozed.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     * @throws InvalidArgumentException if datetime argument is invalid.
     */
    private void snoozeDeadline(Task task, TaskList taskList, Ui ui, StorageManager storageManager)
            throws InvalidArgumentException {
        String description = task.getDescription();
        String date = ui.getTimeStamp();
        date = parseDateTime(date);
        if (isDateValid(date)) {
            Deadline deadline = new Deadline(description, date);
            taskList.deleteTask(this.index);
            taskList.addTaskToIndex(this.index, deadline);
            ui.printSnoozeMessage(deadline);
            storageManager.writeTaskList(taskList);
        } else {
            throw new InvalidArgumentException("Timestamp given is invalid! Please try again.");
        }
    }

    /**
     * Snoozes Event tasks.
     *
     * @param task           Event task to be snoozed.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     * @throws InvalidArgumentException if datetime argument is invalid.
     */
    private void snoozeEvent(Task task, TaskList taskList, Ui ui, StorageManager storageManager)
            throws InvalidArgumentException {
        String description = task.getDescription();
        String startDate = ui.getTimeStamp();
        String endDate = ui.getTimeStamp();
        startDate = parseDateTime(startDate);
        endDate = parseDateTime(endDate);
        if (isDateValid(startDate) && isDateValid(endDate)) {
            Event event = new Event(description, startDate, endDate);
            taskList.deleteTask(this.index);
            taskList.addTaskToIndex(this.index, event);
            ui.printSnoozeMessage(event);
            storageManager.writeTaskList(taskList);
        } else {
            throw new InvalidArgumentException("Timestamp given is invalid! Please try again.");
        }
    }

    /**
     * Snoozes Todo tasks.
     *
     * @param task           Todo task to be snoozed.
     * @param taskList       Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     * @throws InvalidArgumentException if datetime argument is invalid.
     */
    private void snoozeTodo(Task task, TaskList taskList, Ui ui, StorageManager storageManager)
            throws InvalidArgumentException {
        String description = task.getDescription();
        String date = ui.getTimeStamp();
        if (isDateValid(date)) {
            Todo todo = new Todo(description, date);
            taskList.deleteTask(this.index);
            taskList.addTaskToIndex(this.index, todo);
            ui.printSnoozeMessage(todo);
            storageManager.writeTaskList(taskList);
        } else {
            throw new InvalidArgumentException("Timestamp given is invalid! Please try again.");
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
