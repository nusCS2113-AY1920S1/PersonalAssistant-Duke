package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

/**
 * Mark a task as done.
 */
public class DoneCommand extends Command {

    public static final String MESSAGE_USAGE = "done\n\t"
            + "Format: done /id <num>\n\n\t"
            + "Note: content in \"<>\": need to be fulfilled by the user\n\n"
            + "This command will mark a task as done\n"
            + "Examples:\n\t"
            + "done /id 1\n\t\t"
            + "mark the task with id 1 as done";
    public static final String COMMAND_PREFIX = "Noted. I have mark the below task as done: \n";
    public static final String COMMAND_ALR_DONE = "Task to be mark done is already marked as done! \n";
    private int taskID;

    public DoneCommand(int taskID) {
        this.taskID = taskID;
    }

    @Override
    public CommandResult commandExecute(TaskList taskList) throws CommandException {
        Task task = taskList.getTaskById(taskID);
        Boolean isDone = task.getisDone();
        if (!isDone) {
            task.markAsDone();
            return new CommandResult(COMMAND_PREFIX.concat(task.toString()), true);
        } else {
            return new CommandResult(COMMAND_ALR_DONE.concat(task.toString()), false);
        }

    }
}