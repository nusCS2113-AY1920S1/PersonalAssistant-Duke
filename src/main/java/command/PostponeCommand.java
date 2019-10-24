package command;

import exception.DukeException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.time.LocalDateTime;

/**
 * Postpones a task to different times.
 *
 * @author Tan Yi Xiang
 * @version 1.4
 */
public class PostponeCommand extends Command {

    private int indexOfTask;
    private LocalDateTime toDate = null;
    private LocalDateTime startDate;

    private static final String POSTPONED_DEADLINE = "Got it! I've postponed this deadline:\n";
    private static final String POSTPONED_EVENT = "Got it! I've postponed this event:\n";
    private static final String TOBEFIXED = "Postpone for TODO to be fixed once TODO overhaul completed.";

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
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        if (!isIndexValid(indexOfTask, tasks.getSize())) {
            throw new DukeException(DukeException.taskDoesNotExist());
        }

        Task taskToBePostponed = tasks.getTasks().get(indexOfTask);
        String description = taskToBePostponed.description;
        if (isDeadline(taskToBePostponed)) {
            if (isDeadlineClash(description, startDate, tasks)) {
                throw new DukeException(DukeException.taskClash());
            } else {
                taskToBePostponed.setStartDate(startDate);
                storage.saveFile(tasks.getTasks());
                Ui.printOutput(POSTPONED_DEADLINE + taskToBePostponed.toString());
            }
        } else if (isEvent(taskToBePostponed)) {
            if (isEventClash(description, startDate, toDate, tasks)) {
                throw new DukeException(DukeException.taskClash());
            } else {
                taskToBePostponed.setStartDate(startDate);
                taskToBePostponed.setEndDate(toDate);
                storage.saveFile(tasks.getTasks());
                Ui.printOutput(POSTPONED_EVENT + taskToBePostponed.toString());
            }
        } else {
            Ui.printOutput(TOBEFIXED);
        }
    }


    private boolean isDeadline(Task task) {
        return task.endDate == null;
    }

    private boolean isEvent(Task task) {
        return task.endDate != null && task.startDate != null;
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
