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
 * @version 1.7
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
        String description = taskToBePostponed.getDescription();
        if (isDeadline(taskToBePostponed)) {
            if (isDeadlineClash(description, startDate, tasks)) {
                throw new ChronologerException(ChronologerException.taskClash());
            } else {
                postponeDate(taskToBePostponed, startDate, tasks, storage);
                UiTemporary.printOutput(POSTPONED_DEADLINE + taskToBePostponed.toString());
            }
        } else if (isEvent(taskToBePostponed)) {
            if (isEventClash(description, startDate, toDate, tasks)) {
                throw new ChronologerException(ChronologerException.taskClash());
            } else {
                postponeDateRange(taskToBePostponed, startDate, toDate, tasks, storage);
                UiTemporary.printOutput(POSTPONED_EVENT + taskToBePostponed.toString());
            }
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
        taskToBePostponed.setStartDate(startDate);
        ChronologerStateList.addState((tasks.getTasks()));
        tasks.updatePriority(null);
        storage.saveFile(tasks.getTasks());
    }

    private boolean isDeadline(Task task) {
        return (DEADLINE.equals(task.getType()));
    }

    private boolean isEvent(Task task) {
        return (EVENT.equals(task.getType()));
    }

    private boolean isTodoPeriod(Task task) {
        return (TODO_PERIOD.equals(task.getType()));
    }


}
