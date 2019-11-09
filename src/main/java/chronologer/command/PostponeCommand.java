package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.Deadline;
import chronologer.task.Event;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.UiTemporary;

import java.time.LocalDateTime;

/**
 * Postpones a task to different times.
 *
 * @author Tan Yi Xiang
 * @version 1.9
 */
public class PostponeCommand extends Command {

    private static final String DEADLINE = "DEADLINE";
    private static final String EVENT = "EVENT";
    private static final String TODO_PERIOD = "TODO PERIOD";

    private int indexOfTask;
    private LocalDateTime toDate = null;
    private LocalDateTime startDate;

    private static final String POSTPONED_DEADLINE = "Got it! I've postponed this deadline:\n";
    private static final String POSTPONED_EVENT = "Got it! I've postponed this event:\n";
    private static final String POSTPONED_TODO = "Got it! I've postponed this TODO:\n";
    private static final String UNABLE_TO_POSTPONE = "Timeless and Duration based TODO tasks can't be postponed.";

    /**
     * Initializes the different parameters to postpone a task.
     *
     * @param indexOfTask Holds the index of the task.
     * @param startDate   Holds the start date of the task.
     */
    public PostponeCommand(int indexOfTask, LocalDateTime startDate) {
        this.indexOfTask = indexOfTask;
        this.startDate = startDate;
    }

    /**
     * Secondary constructor for event tasks in particular.
     *
     * @param indexOfTask Holds the index of the task to be commented on.
     * @param startDate   Holds the new start time of a task.
     * @param toDate      Holds the new end time of a task.
     */
    public PostponeCommand(int indexOfTask, LocalDateTime startDate, LocalDateTime toDate) {
        this.indexOfTask = indexOfTask;
        this.startDate = startDate;
        this.toDate = toDate;
    }

    /**
     * Postpones a task properly and saves the updated TaskList  it to persistent storage.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        isIndexValid(tasks);
        Task taskToBePostponed = tasks.getTasks().get(indexOfTask);

        if (isDeadlinePostponeable(taskToBePostponed, tasks)) {
            postponeDate(taskToBePostponed, startDate, tasks, storage);
            UiTemporary.printOutput(POSTPONED_DEADLINE + taskToBePostponed.toString());

        } else if (isEventPostponeable(taskToBePostponed, tasks)) {
            postponeDateRange(taskToBePostponed, startDate, toDate, tasks, storage);
            UiTemporary.printOutput(POSTPONED_EVENT + taskToBePostponed.toString());

        } else if (isTodoPeriod(taskToBePostponed)) {
            postponeDateRange(taskToBePostponed, startDate, toDate, tasks, storage);
            UiTemporary.printOutput(POSTPONED_TODO + taskToBePostponed.toString());

        } else {
            UiTemporary.printOutput(UNABLE_TO_POSTPONE);
        }
    }

    /**
     * Check whether index of list item within current list range.
     *
     * @param tasks Current task list
     */
    private void isIndexValid(TaskList tasks) throws ChronologerException {
        if (!isIndexValid(indexOfTask, tasks.getSize())) {
            UiTemporary.printOutput(ChronologerException.taskDoesNotExist());
            throw new ChronologerException(ChronologerException.taskDoesNotExist());
        }
    }

    /**
     * Check if task is deadline and doesn't clash with other deadlines at the same time.
     *
     * @param taskToBePostponed The task to be postponed
     * @param tasks             The list of tasks
     * @return True if task is a deadline and doesn't clash
     */
    private boolean isDeadlinePostponeable(Task taskToBePostponed, TaskList tasks) throws ChronologerException {

        if (isDeadline(taskToBePostponed)) {
            if (isDeadlineClash(taskToBePostponed.getDescription(), startDate, tasks)) {
                UiTemporary.printOutput(ChronologerException.taskClash());
                throw new ChronologerException(ChronologerException.taskClash());
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if task is event and doesn't clash with other events at the same time.
     *
     * @param taskToBePostponed The task to be postponed
     * @param tasks             The list of tasks
     * @return True if task is an event and doesn't clash
     */
    private boolean isEventPostponeable(Task taskToBePostponed, TaskList tasks) throws ChronologerException {
        if (isEvent(taskToBePostponed)) {
            if (isEventClash(taskToBePostponed.getDescription(), startDate, toDate, tasks)) {
                UiTemporary.printOutput(ChronologerException.taskClash());
                throw new ChronologerException(ChronologerException.taskClash());
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if the date to be postponed is clashing with another deadline.
     *
     * @param description Description of the deadline
     * @param startDate   Start date  of the deadline
     * @param tasks       Current task list
     * @return Boolean value that indicate whether a clash will occur
     */
    private boolean isDeadlineClash(String description, LocalDateTime startDate, TaskList tasks) {
        Deadline deadlineTest = new Deadline(description, startDate);
        return tasks.isClash(deadlineTest);
    }

    /**
     * Determine if the date to be postponed is clashing with another event.
     *
     * @param description Description of the event
     * @param startDate   Start date  of the event
     * @param tasks       Current task list
     * @return Boolean value that indicate whether a clash will occur
     */
    private boolean isEventClash(String description, LocalDateTime startDate, LocalDateTime endDate, TaskList tasks) {
        Event eventTest = new Event(description, startDate, endDate);
        return tasks.isClash(eventTest);
    }

    /**
     * Postpone task with a specific date range eg: event, todo with period.
     *
     * @param taskToBePostponed The task to have its date postponed
     * @param startDate         Start date of the task
     * @param toDate            End date of the task
     * @param tasks             Current task list
     * @param storage           Storage component
     * @throws ChronologerException If errors occur in storage component
     */
    private void postponeDateRange(Task taskToBePostponed, LocalDateTime startDate,
                                   LocalDateTime toDate, TaskList tasks, Storage storage) throws ChronologerException {
        checkEventTodoDate(startDate, taskToBePostponed.getStartDate(), toDate, taskToBePostponed.getEndDate());
        taskToBePostponed.setStartDate(startDate);
        taskToBePostponed.setEndDate(toDate);
        ChronologerStateList.addState((tasks.getTasks()));
        tasks.updatePriority(null);
        storage.saveFile(tasks.getTasks());
    }


    /**
     * Postpone task with a single date eg: deadline.
     *
     * @param taskToBePostponed The task to have its date postponed
     * @param startDate         Start date of the task
     * @param tasks             Current task list
     * @param storage           Storage component
     * @throws ChronologerException If errors occur in storage component
     */
    private void postponeDate(Task taskToBePostponed, LocalDateTime startDate,
                              TaskList tasks, Storage storage) throws ChronologerException {
        checkDeadlineDate(startDate, taskToBePostponed.getStartDate());
        taskToBePostponed.setStartDate(startDate);
        ChronologerStateList.addState((tasks.getTasks()));
        tasks.updatePriority(null);
        storage.saveFile(tasks.getTasks());
    }

    /**
     * Check if new deadline date later than old deadline date.
     *
     * @param newStartDate New deadline date
     * @param oldStartDate Old deadline date
     * @throws ChronologerException If new deadline date earlier than old deadline date
     */
    private void checkDeadlineDate(LocalDateTime newStartDate, LocalDateTime oldStartDate) throws ChronologerException {
        if (newStartDate.isBefore(oldStartDate)) {
            UiTemporary.printOutput(ChronologerException.postponeDateError());
            throw new ChronologerException(ChronologerException.postponeDateError());
        }
    }

    /**
     * Check if event/todo dates are later than the old dates and also checks if enddate later than startdate.
     *
     * @param newStartDate New event/Todo start date
     * @param oldStartDate Old event/Todo start date
     * @param newEndDate   New event/Todo end date
     * @param oldEndDate   Old event/Todo end date
     * @throws ChronologerException If new event dates later than old event dates and if endate earlier than startdate
     */
    private void checkEventTodoDate(LocalDateTime newStartDate, LocalDateTime oldStartDate, LocalDateTime
        newEndDate, LocalDateTime oldEndDate) throws ChronologerException {
        if (newStartDate.isBefore(oldStartDate) || newEndDate.isBefore(oldEndDate)) {
            UiTemporary.printOutput(ChronologerException.postponeDateError());
            throw new ChronologerException(ChronologerException.postponeDateError());
        }

        if (newEndDate.isBefore(newStartDate)) {
            UiTemporary.printOutput(ChronologerException.endDateError());
            throw new ChronologerException(ChronologerException.endDateError());
        }

    }

    /**
     * Check whether the task is of deadline type.
     *
     * @return True if the task is a deadline.
     */
    private boolean isDeadline(Task task) {
        return (DEADLINE.equals(task.getType()));
    }

    /**
     * Check whether the task is of event type.
     *
     * @return True if the task is an event.
     */
    private boolean isEvent(Task task) {
        return (EVENT.equals(task.getType()));
    }

    /**
     * Check whether the task is of todo period type.
     *
     * @return True if the task is a todo period.
     */
    private boolean isTodoPeriod(Task task) {
        return (TODO_PERIOD.equals(task.getType()));
    }


}
