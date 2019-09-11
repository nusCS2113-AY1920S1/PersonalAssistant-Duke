package command;

import exception.DukeException;
import task.Task;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.TaskList;
import task.Todo;
import ui.Ui;

/**
 * The AddCommand class is used when the user has input a command which requires a task to be added to the TaskList
 *
 * @author Sai Ganesh Suresh
 * @version v1.0
 */
public class AddCommand extends Command {

    private String command;
    private String taskFeatures;
    private String formattedDateTime;

    /**
     * This AddCommand function is used to assign the different parameters required when adding a task.
     *
     * @param command this string holds command type determinant to decide how to process the user input.
     * @param taskFeatures this string holds the description of the task provided by the user.
     * @param formattedDateTime this string contains the formatted user input that has the desired date time format.
     */
    public AddCommand(String command, String taskFeatures, String formattedDateTime) {
        this.command = command;
        this.taskFeatures = taskFeatures;
        this.formattedDateTime = formattedDateTime;
    }

    /**
     * This execute function is used to add the respective tasks to the TaskList and save to persistent storage.
     *
     * @param tasks this string holds command type determinant to decide how to process the user input.
     * @param ui this string holds the description of the task provided by the user.
     * @param storage this parameter provides the execute function the storage to allow the saving of the file.
     *
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        Task task;
        switch (command) {
        case "todo":
            task = new Todo(taskFeatures);
            break;
        case "deadline":
            task = new Deadline(taskFeatures, formattedDateTime);
            break;
        case "event":
            task = new Event(taskFeatures, formattedDateTime);
            break;
        default:
            throw new DukeException(DukeException.UNKNOWN_USER_COMMAND());
        }

        tasks.add(task);
        storage.saveFile(tasks.getTasks());
        Ui.printOutput("Got it! I've added this task:" + "\n  " + task.toString() +"\nNow you have " +
                        tasks.getSize() + " task(s) in the list.");
    }
}