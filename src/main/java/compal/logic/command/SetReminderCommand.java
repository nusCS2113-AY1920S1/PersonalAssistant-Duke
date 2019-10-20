package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

public class SetReminderCommand extends Command {

    private int taskID;
    private String status;

    public static final String MESSAGE_INVALID_INPUT = "Error: Invalid input!";
    public static final String COMMAND_PREFIX = "Noted. I have changed the reminder status of this task.\n";

    public SetReminderCommand(int taskID, String status) {
        this.taskID = taskID;
        this.status = status;
    }

    @Override
    public CommandResult commandExecute(TaskList taskList) throws CommandException {
        Task task = taskList.getTaskById(taskID);
        boolean state;
        if ("Y".equals(status)) {
            state = true;
        } else  if ("N".equals(status)) {
            state = false;
        } else {
            throw new CommandException(MESSAGE_INVALID_INPUT);
        }
        task.setHasReminder(state);
        return new CommandResult(COMMAND_PREFIX.concat(task.toString()),true);
    }
}
