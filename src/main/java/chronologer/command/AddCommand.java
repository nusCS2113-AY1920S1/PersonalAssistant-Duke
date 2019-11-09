package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.task.Deadline;
import chronologer.task.Event;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.storage.Storage;
import chronologer.task.Todo;
import chronologer.ui.UiMessageHandler;
import java.time.LocalDateTime;

/**
 * Adds a task to TaskList if the user input is correctly parsable.
 *
 * @author Sai Ganesh Suresh
 * @version v1.3
 */
public class AddCommand extends Command {

    protected String command;
    protected String taskDescription;
    protected LocalDateTime formattedStartDate;
    protected LocalDateTime formattedEndDate;
    protected int duration = 0;
    protected String modCode;

    /**
     * Initializes the different parameters when adding a task.
     *
     * @param command         Holds the command type.
     * @param taskDescription Holds the description of the task provided by the
     *                        user.
     * @param startDate       Holds the start date of the task.
     * @param endDate         Holds the end date of the task.
     * @param modCode         Holds the module code of the task, if any
     */
    public AddCommand(String command, String taskDescription, LocalDateTime startDate, LocalDateTime endDate,
            String modCode) {

        this.command = command;
        this.taskDescription = taskDescription;
        this.formattedStartDate = startDate;
        this.formattedEndDate = endDate;
        this.modCode = modCode;
    }

    /**
     * Initializes the different parameters when adding a task.
     *
     * @param command         Holds the command type.
     * @param taskDescription Holds the description of the task provided by the
     *                        user.
     * @param duration        Holds the duration period the task.
     */
    public AddCommand(String command, String taskDescription, Integer duration) {
        this.command = command;
        this.taskDescription = taskDescription;
        this.duration = duration;
    }

    /**
     * Initializes the different parameters when adding a task.
     * 
     * @param command         Holds the command type.
     * @param taskDescription Holds the description of the task provided by the
     *                        user.
     * @param startDate       Holds the start date of the task.
     * @param endDate         Holds the end date of the task.
     */
    public AddCommand(String command, String taskDescription, LocalDateTime startDate, LocalDateTime endDate) {
        this.command = command;
        this.taskDescription = taskDescription;
        this.formattedStartDate = startDate;
        this.formattedEndDate = endDate;
    }

    /**
     * Adds the task to the TaskList and saves the updated TaskList to persistent
     * storage.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        Task task;
        switch (command.toLowerCase()) {
        case "todo":
            if (formattedStartDate != null) {
                task = new Todo(taskDescription, formattedStartDate, formattedEndDate);
            } else if (duration != 0) {
                task = new Todo(taskDescription, duration);
            } else {
                task = new Todo(taskDescription);
            }
            break;
        case "deadline":
            task = new Deadline(taskDescription, formattedStartDate);
            if (tasks.isClash(task)) {
                UiMessageHandler.outputMessage(ChronologerException.taskClash());
                throw new ChronologerException(ChronologerException.taskClash());
            }
            break;
        case "event":
            task = new Event(taskDescription, formattedStartDate, formattedEndDate);
            if (tasks.isClash(task)) {
                UiMessageHandler.outputMessage(ChronologerException.taskClash());
                throw new ChronologerException(ChronologerException.taskClash());
            }
            break;
        case "assignment":
            task = new Deadline(taskDescription, formattedStartDate, modCode);
            if (tasks.isClash(task)) {
                UiMessageHandler.outputMessage(ChronologerException.taskClash());
                throw new ChronologerException(ChronologerException.taskClash());
            }
            break;
        case "exam":
            task = new Event(taskDescription, formattedStartDate, formattedEndDate, modCode);
            if (tasks.isClash(task)) {
                UiMessageHandler.outputMessage(ChronologerException.taskClash());
                throw new ChronologerException(ChronologerException.taskClash());
            }
            break;
        default:
            UiMessageHandler.outputMessage(ChronologerException.unknownUserCommand());
            throw new ChronologerException(ChronologerException.unknownUserCommand());
        }

        tasks.add(task);
        ChronologerStateList.addState(tasks.getTasks());
        storage.saveFile(tasks.getTasks());
        UiMessageHandler.outputMessage("Got it! I've added this task:" + "\n  " + task.toString() + "\nNow you have "
                + tasks.getSize() + " task(s) in the list.");

    }
}