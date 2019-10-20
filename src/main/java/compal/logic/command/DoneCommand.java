package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

/**
 * Mark a task as done.
 */
public class DoneCommand extends Command {

    private int taskID;

    public static final String COMMAND_PREFIX = "Noted. I have mark the below task as done: \n";
    public static final String COMMAND_ALR_DONE = "Task to be mark done is already marked as done! \n";

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
            return new CommandResult(COMMAND_ALR_DONE.concat(task.toString()),false);
        }

    }
}