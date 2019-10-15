package command;

import exception.DukeException;
import task.*;
import storage.Storage;
import ui.Ui;

import java.time.LocalDateTime;

/**
 * The AddCommand class is used when the user has input a command which requires
 * a task to be added to the TaskList.
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class AddCommand extends Command {

    private String command;
    private String taskFeatures;
    private LocalDateTime formattedStartDate;
    private LocalDateTime formattedEndDate;
    private int duration = 0;

    /**
     * This AddCommand function is used to assign the different parameters required
     * when adding a task.
     *
     * @param command      this string holds command type determinant to decide how
     *                     to process the user input.
     * @param taskFeatures this string holds the description of the task provided by
     *                     the user.
     * @param startDate       string contains the formatted user input that has the
     *                     desired date time format.
     * @param endDate       string contains the formatted user input that has the
     *                     desired date time format.
     */

    public AddCommand(String command, String taskFeatures, LocalDateTime startDate, LocalDateTime endDate) {

        this.command = command;
        this.taskFeatures = taskFeatures;
        this.formattedStartDate = startDate;
        this.formattedEndDate = endDate;
    }

    /**
     * Creates an AddCommand based on duration of the task.
     * @param command       holds command type determinant to decide how
     *                      to process the user input.
     * @param taskFeatures  holds the description of the task provided by
     *                      the user.
     * @param duration      holds the duration period of how long the task should last
     */
    public AddCommand(String command, String taskFeatures, int duration) {
        this.command = command;
        this.taskFeatures = taskFeatures;
        this.duration = duration;
    }

    /**
     * This execute function is used to add the respective tasks to the TaskList and
     * save to persistent storage.
     *
     * @param tasks   this string holds command type determinant to decide how to
     *                process the user input.
     * @param storage this parameter provides the execute function the storage to
     *                allow the saving of the file.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        Task task;
        switch (command) {
        case "todo":
            if (formattedStartDate != null) {
                task = new TodoWithinPeriod(taskFeatures, formattedStartDate, formattedEndDate);
            } else if (duration != 0) {
                task = new TodoWithDuration(taskFeatures, duration);
            } else {
                task = new Todo(taskFeatures);
            }
            break;
        case "deadline":
            task = new Deadline(taskFeatures, formattedStartDate);
            if (tasks.isClash(task)) {
                throw new DukeException(DukeException.taskClash());
            }
            break;
        case "event":
            task = new Event(taskFeatures, formattedStartDate, formattedEndDate);
            if (tasks.isClash(task)) {
                throw new DukeException(DukeException.taskClash());
            }
            break;
        default:
            throw new DukeException(DukeException.unknownUserCommand());
        }

        tasks.add(task);
        storage.saveFile(tasks.getTasks());
        Ui.printOutput("Got it! I've added this task:" + "\n  " + task.toString() + "\nNow you have " + tasks.getSize()
            + " task(s) in the list.");

    }
}