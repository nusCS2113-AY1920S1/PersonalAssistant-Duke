package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.Storage;
import chronologer.task.Deadline;
import chronologer.task.Event;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.Ui;
import chronologer.ui.UiTemporary;

import java.time.LocalDateTime;

/**
 * Postpones a task to different times.
 *
 * @author Tan Yi Xiang
 * @version 1.5
 */
public class PostponeCommand extends Command {

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
        if (!isIndexValid(indexOfTask, tasks.getSize())) {
            UiTemporary.printOutput(ChronologerException.taskDoesNotExist());
            throw new ChronologerException(ChronologerException.taskDoesNotExist());
        }

        Task taskToBePostponed = tasks.getTasks().get(indexOfTask);
        String description = taskToBePostponed.getDescription();
        if (tasks.isDeadline(taskToBePostponed)) {
            if (isDeadlineClash(description, startDate, tasks)) {
                throw new ChronologerException(ChronologerException.taskClash());
            } else {
                taskToBePostponed.setStartDate(startDate);
                storage.saveFile(tasks.getTasks());
                UiTemporary.printOutput(POSTPONED_DEADLINE + taskToBePostponed.toString());
            }
        } else if (tasks.isEvent(taskToBePostponed)) {
            if (isEventClash(description, startDate, toDate, tasks)) {
                throw new ChronologerException(ChronologerException.taskClash());
            } else {
                taskToBePostponed.setStartDate(startDate);
                taskToBePostponed.setEndDate(toDate);
                storage.saveFile(tasks.getTasks());
                UiTemporary.printOutput(POSTPONED_EVENT + taskToBePostponed.toString());
            }
        } else if (tasks.isTodoPeriod(taskToBePostponed)) {
            taskToBePostponed.setStartDate(startDate);
            taskToBePostponed.setEndDate(toDate);
            storage.saveFile(tasks.getTasks());
            UiTemporary.printOutput(POSTPONED_TODO + taskToBePostponed.toString());
        } else {
            UiTemporary.printOutput(UNABLE_TO_POSTPONE);
        }
    }

    private boolean isDeadlineClash(String description, LocalDateTime startDate, TaskList tasks) {
        Deadline deadlineTest = new Deadline(description, startDate);
        return tasks.isClash(deadlineTest);
    }

    private boolean isEventClash(String description, LocalDateTime startDate, LocalDateTime endDate, TaskList tasks) {
        Event eventTest = new Event(description, startDate, endDate);
        return tasks.isClash(eventTest);
    }


}
