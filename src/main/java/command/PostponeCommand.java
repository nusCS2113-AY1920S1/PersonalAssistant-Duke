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
 * @version 1.3
 */
public class PostponeCommand extends Command {

    private int indexOfTask;
    private LocalDateTime fromDate = null;
    private LocalDateTime toDate = null;
    private LocalDateTime startDate = null;


    public PostponeCommand(int indexOfTask, LocalDateTime startDate) {
        this.indexOfTask = indexOfTask;
        this.startDate = startDate;
    }

    /**
     * Secondary constructor for event tasks in particular.
     *
     * @param indexOfTask Holds the index of the task to be commented on.
     * @param fromDate    Holds the new start time of a task.
     * @param toDate      Holds the new end time of a task.
     */
    public PostponeCommand(int indexOfTask, LocalDateTime fromDate, LocalDateTime toDate) {
        this.indexOfTask = indexOfTask;
        this.fromDate = fromDate;
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
        if (isIndexValid(indexOfTask, tasks.getSize())) {

            Task taskToBePostponed = tasks.getTasks().get(indexOfTask);
            String description = taskToBePostponed.description;

            if (taskToBePostponed.toString().contains("[D]")) {
                if (startDate == null) {
                    throw new DukeException(DukeException.wrongDateOrTime());
                }
                Deadline deadlineTest = new Deadline(description, startDate);
                if (tasks.isClash(deadlineTest)) {
                    throw new DukeException(DukeException.taskClash());
                } else {
                    taskToBePostponed.setStartDate(startDate);
                    storage.saveFile(tasks.getTasks());
                    Ui.printOutput("Got it! I've postponed this deadline:\n " + taskToBePostponed.toString());
                }
            } else if (taskToBePostponed.toString().contains("[E]")) {
                if (fromDate == null || toDate == null) {
                    throw new DukeException(DukeException.wrongDateOrTime());
                }
                Event eventTest = new Event(description, fromDate, toDate);
                if (tasks.isClash(eventTest)) {
                    throw new DukeException(DukeException.taskClash());
                } else {
                    taskToBePostponed.setStartDate(fromDate);
                    taskToBePostponed.setEndDate(toDate);
                    storage.saveFile(tasks.getTasks());
                    Ui.printOutput("Got it! I've postponed this event:\n " + taskToBePostponed.toString());
                }
            } else {
                Ui.printOutput("This task can't be scheduled");
            }
        }
    }
}
