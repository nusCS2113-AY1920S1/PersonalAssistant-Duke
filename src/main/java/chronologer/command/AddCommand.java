package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.task.Deadline;
import chronologer.task.Event;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.storage.Storage;
import chronologer.task.Todo;
import chronologer.ui.UiTemporary;

import java.time.LocalDateTime;

/**
 * Adds a task to TaskList if the user input is correctly parsable.
 *
 * @author Sai Ganesh Suresh
 * @version v1.3
 */
public class AddCommand extends Command {

    private String command;
    private String taskFeatures;
    private LocalDateTime formattedStartDate;
    private LocalDateTime formattedEndDate;
    private int duration = 0;

    /**
     * Initializes the different parameters when adding a task.
     *
     * @param command      Holds the command type.
     * @param taskFeatures Holds the description of the task provided by the user.
     * @param startDate    Holds the start date of the task.
     * @param endDate      Holds the end date of the task.
     */

    public AddCommand(String command, String taskFeatures, LocalDateTime startDate, LocalDateTime endDate) {

        this.command = command;
        this.taskFeatures = taskFeatures;
        this.formattedStartDate = startDate;
        this.formattedEndDate = endDate;
    }

    /**
     * Initializes the different parameters when adding a task.
     *
     * @param command       Holds the command type.
     * @param taskFeatures  Holds the description of the task provided by the user.
     * @param duration      Holds the duration period the task.
     */
    public AddCommand(String command, String taskFeatures, Integer duration) {
        this.command = command;
        this.taskFeatures = taskFeatures;
        this.duration = duration;
    }

    /**
     * Adds the task to the TaskList and saves the updated TaskList to persistent storage.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        Task task;
        switch (command) {
        case "todo":
            if (formattedStartDate != null) {
                task = new Todo(taskFeatures, formattedStartDate, formattedEndDate);
            } else if (duration != 0) {
                task = new Todo(taskFeatures, duration);
            } else {
                task = new Todo(taskFeatures);
            }
            break;
        case "deadline":
            task = new Deadline(taskFeatures, formattedStartDate);
            if (tasks.isClash(task)) {
                UiTemporary.printOutput(ChronologerException.taskClash());
                throw new ChronologerException(ChronologerException.taskClash());
            }
            break;
        case "event":
            task = new Event(taskFeatures, formattedStartDate, formattedEndDate);
            if (tasks.isClash(task)) {
                UiTemporary.printOutput(ChronologerException.taskClash());
                throw new ChronologerException(ChronologerException.taskClash());
            }
            break;
        default:
            throw new ChronologerException(ChronologerException.unknownUserCommand());
        }

        tasks.add(task);
        storage.saveFile(tasks.getTasks());
        UiTemporary.printOutput("Got it! I've added this task:" + "\n  " + task.toString()
            + "\nNow you have " + tasks.getSize() + " task(s) in the list.");

    }
}