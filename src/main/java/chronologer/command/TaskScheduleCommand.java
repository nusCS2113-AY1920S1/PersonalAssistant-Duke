package chronologer.command;

import chronologer.TaskScheduler;
import chronologer.exception.ChronologerException;
import chronologer.storage.Storage;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.task.Deadline;
import chronologer.task.Event;
import chronologer.task.Todo;
import chronologer.ui.UiTemporary;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Finds a free period of time within the user's schedule for a selected duration value.
 *
 * @author Fauzan Adipratama
 * @version 1.3
 */
public class TaskScheduleCommand extends Command {
    private static final String NOT_TODO = "Task selected is not a Todo with a duration";
    private static final String NOT_DEADLINE = "Task selected is not a Deadline";
    private static final String OVERDUE_DEADLINE = "The selected deadline is overdue!";

    private final Long durationToSchedule;
    private final Integer indexOfTask;
    private final Integer indexOfDeadline;
    private final LocalDateTime deadlineDate;

    /**
     * Initialises the command parameter for a selected task to be done by a selected deadline.
     * @param indexOfTask is the index number of the selected task in the TaskList
     * @param indexDeadline is the index number of the selected deadline in the TaskList
     */
    public TaskScheduleCommand(int indexOfTask, int indexDeadline) {
        this.durationToSchedule = null;
        this.indexOfTask = indexOfTask;
        this.indexOfDeadline = indexDeadline;
        this.deadlineDate = null;
    }

    /**
     * Initialises the command parameter for a selected task to be done by an inputted date.
     * @param indexOfTask is the index number of the selected task in the TaskList
     * @param deadlineDate is the date to schedule the task by
     */
    public TaskScheduleCommand(int indexOfTask, LocalDateTime deadlineDate) {
        this.durationToSchedule = null;
        this.indexOfTask = indexOfTask;
        this.indexOfDeadline = null;
        this.deadlineDate = deadlineDate;
    }

    /**
     * Initialises the command parameter for an inputted duration to be done by a selected deadline.
     * @param duration is the minimum hours of a free period that the user wishes to find
     * @param indexOfDeadline is the index number of the selected deadline in the TaskList
     */
    public TaskScheduleCommand(Long duration, int indexOfDeadline) {
        this.durationToSchedule = duration;
        this.indexOfTask = null;
        this.indexOfDeadline = indexOfDeadline;
        this.deadlineDate = null;
    }

    /**
     * Initialises the command parameter for an inputted duration to be done by an inputted date.
     * @param duration is the minimum hours of a free period that the user wishes to find
     * @param deadlineDate is the date to schedule the task by
     */
    public TaskScheduleCommand(Long duration, LocalDateTime deadlineDate) {
        this.durationToSchedule = duration;
        this.indexOfTask = null;
        this.indexOfDeadline = null;
        this.deadlineDate = deadlineDate;
    }

    /**
     * Retrieves the duration and deadline date based on the user's input and pass the values along
     * to the TaskScheduler logic.
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     * @throws ChronologerException if the selected task is not a compatible type.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        Long duration;
        LocalDateTime deadlineDate;
        String result;

        ArrayList<Task> list = tasks.getTasks();
        duration = retrieveDuration(list);
        deadlineDate = retrieveDeadlineDate(list);

        if (deadlineDate == null) {
            result = TaskScheduler.scheduleTask(tasks, duration);
            UiTemporary.printOutput(result);
            return;
        }

        if (LocalDateTime.now().isAfter(deadlineDate)) {
            UiTemporary.printOutput(OVERDUE_DEADLINE);
            throw new ChronologerException(OVERDUE_DEADLINE);
        }

        result = TaskScheduler.scheduleByDeadline(tasks, duration, deadlineDate);
        UiTemporary.printOutput(result);
    }

    private long retrieveDuration(ArrayList<Task> list) throws ChronologerException {
        if (this.indexOfTask == null) {
            assert this.durationToSchedule != null;
            return this.durationToSchedule;
        }
        if (indexOfTask < 0 || indexOfTask >= list.size()) {
            UiTemporary.printOutput(ChronologerException.invalidIndex());
            throw new ChronologerException(ChronologerException.invalidIndex());
        }

        Todo todo;
        try {
            todo = (Todo) list.get(indexOfTask);
        } catch (ClassCastException e) {
            UiTemporary.printOutput(NOT_TODO);
            throw new ChronologerException(NOT_TODO);
        }
        return todo.duration;
    }

    private LocalDateTime retrieveDeadlineDate(ArrayList<Task> list) throws ChronologerException {
        if (this.indexOfDeadline == null) {
            return this.deadlineDate;
        }
        if (indexOfDeadline < 0 || indexOfDeadline >= list.size()) {
            UiTemporary.printOutput(ChronologerException.invalidIndex());
            throw new ChronologerException(ChronologerException.invalidIndex());
        }

        Deadline deadline;
        try {
            deadline = (Deadline) list.get(indexOfDeadline);
        } catch (ClassCastException e) {
            UiTemporary.printOutput(NOT_DEADLINE);
            throw new ChronologerException(NOT_DEADLINE);
        }
        return deadline.getStartDate();
    }
}