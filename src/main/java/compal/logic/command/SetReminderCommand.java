package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

//@@author Catherinetan99
public class SetReminderCommand extends Command {

    public static final String MESSAGE_USAGE = "set-reminder\n\t"
            + "Format: set-reminder /id <num> /status <Y|N>\n\n\t"
            + "You can switch the order of any two blocks (a block starts with \"/\" and ends by the next block)\n\t"
            + "Note: content in \"<>\": need to be fulfilled by the user\n\t"
            + "content separated by \"|\": must choose exactly one from them\n\n"
            + "This command will set the reminder of the task with id <num> to yes|no\n"
            + "Examples:\n\t"
            + "set-reminder /id 0 /status Y\n\t\t"
            + "set the reminder of task with id 0 to true\n\t"
            + "set-reminder /id 1 /status N\n\t\t"
            + "set the reminder of task with id 1 to false";

    public static final String MESSAGE_INVALID_STATUS_INPUT = "Error: Invalid status input! Enter Y or N.";
    public static final String COMMAND_PREFIX = "Noted. I have changed the reminder status of this task.\n";
    public static final String MESSAGE_INVALID_ID = "Error: Invalid task ID!";
    private int taskID;
    private String status;

    public SetReminderCommand(int taskID, String status) {
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

        boolean state;
        if (status.equalsIgnoreCase("y")) {
            state = true;
        } else if (status.equalsIgnoreCase("n")) {
            state = false;
        } else {
            throw new CommandException(MESSAGE_INVALID_STATUS_INPUT);
        }
        task.setHasReminder(state);
        return new CommandResult(COMMAND_PREFIX.concat(task.toString()), true);
    }
}
