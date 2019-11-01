package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

//@@author yueyeah
/**
 * A Command object that handles looking for a task in the tasklist that corresponds to the given taskID,
 * and removes that specified task from the tasklist.
 */
public class DeleteCommand extends Command {
    /**
     * CONSTANTS.
     */
    public static final String MESSAGE_USAGE = "delete\n\t"
            + "Format: delete /id <num>\n\n\t"
            + "Note: content in \"<>\": need to be fulfilled by the user\n\n"
            + "This command will delete a task with id <num>\n"
            + "Examples:\n\t"
            + "delete /id 1\n\t\t"
            + "delete task with id 1";
    public static final String MESSAGE_INVALID_ID = "Error: Id input does not exist!";
    public static final String MESSAGE_OUTPUT = "The following task has been deleted: \n";
    private int taskID;

    /**
     * Constructor for DeleteCommand object.
     *
     * @param taskID The ID of the target task to be deleted.
     */
    public DeleteCommand(int taskID) {
        this.taskID = taskID;
    }

    /**
     * Looks for the task that corresponds to the taskID, and removes that task from the tasklist.
     *
     * @param taskList The list of tasks.
     * @return CommandResult object that contains the output to be printed for the user.
     * @throws CommandException If no task in the tasklist corresponds to the taskID.
     */
    public CommandResult commandExecute(TaskList taskList) throws CommandException {
        Task task;
        try {
            task = taskList.getTaskById(taskID);
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_INVALID_ID);
        }
        String finalString = MESSAGE_OUTPUT + task.toString();
        taskList.removeTaskById(taskID);
        return new CommandResult(finalString, false);
    }
}
