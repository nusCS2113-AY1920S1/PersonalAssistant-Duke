package command;

import exception.DukeException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.Task;
import task.TaskList;
import ui.Ui;

import javax.swing.UIClientPropertyKey;
import java.lang.reflect.GenericDeclaration;
import java.time.LocalDateTime;

/**
 * Used to postpone a task to different times.
 *
 * @author Tan Yi Xiang
 * @version 1.2
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
     * Secondary constructor for event tasks.
     *
     * @param indexOfTask Index of the task to postpone
     * @param fromDate    New fromDate to represent the new time where event will start
     * @param toDate      New toDate to represent the new time where the event will end
     */
    public PostponeCommand(int indexOfTask, LocalDateTime fromDate, LocalDateTime toDate) {
        this.indexOfTask = indexOfTask;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        if (indexOfTask < 0 || indexOfTask > (tasks.getSize() - 1)) {
            throw new DukeException(DukeException.taskDoesNotExist());
        }
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
