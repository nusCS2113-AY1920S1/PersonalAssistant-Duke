package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

//@@author SholihinK
/**
 * Mark a task as done or undone.
 */
public class DoneCommand extends Command {

    public static final String MESSAGE_USAGE = "done\n\t"
        + "Format: done /id <num>\n\n\t"
        + "Note: content in \"<>\": need to be fulfilled by the user\n\n"
        + "This command will mark a task as done\n"
        + "Examples:\n\t"
        + "done /id 1 /status Y\n\t\t"
        + "mark the task with id 1 as done";

    public static final String MESSAGE_INVALID_INPUT = "Error: Invalid status input!";
    public static final String MESSAGE_INVALID_ID = "Error: Id input does not exist!";

    public static final String COMMAND_PREFIX = "Noted. I have mark the below task as done: \n";
    public static final String COMMAND_PREFIX2 = "Noted. I have mark the below task as not done: \n";
    public static final String COMMAND_ALR_DONE = "Task to be mark done is already marked as done! \n";
    public static final String COMMAND_ALR_UNDONE = "Task to be mark not done is "
        + "already marked as not done! \n";
    private int taskID;
    private String status;

    public DoneCommand(int taskID, String status) {
        this.taskID = taskID;
        this.status = status;
    }

    @Override
    public CommandResult commandExecute(TaskList taskList) throws CommandException {
        Task task;

        try {
            task = taskList.getTaskById(taskID);
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_INVALID_ID);
        }

        Boolean isDone = task.getisDone();

        boolean state;
        if (status.equalsIgnoreCase("y")) {
            state = true;
        } else if (status.equalsIgnoreCase("n")) {
            state = false;
        } else {
            throw new CommandException(MESSAGE_INVALID_INPUT);
        }

        if (!isDone) {
            if (!state) {
                return new CommandResult(COMMAND_ALR_UNDONE.concat(task.toString()), false);
            } else {
                task.markAsDone();
                return new CommandResult(COMMAND_PREFIX.concat(task.toString()), true);
            }
        } else {
            if (state) {
                return new CommandResult(COMMAND_ALR_DONE.concat(task.toString()), false);
            } else {
                task.markAsNotDone();
                return new CommandResult(COMMAND_PREFIX2.concat(task.toString()), false);
            }

        }

    }
}