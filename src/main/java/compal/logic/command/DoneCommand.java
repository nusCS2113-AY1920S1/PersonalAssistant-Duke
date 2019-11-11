package compal.logic.command;

import compal.commons.LogUtils;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.logging.Logger;

//@@author SholihinK

/**
 * Mark a task as done or undone.
 */
public class DoneCommand extends Command {
    private static final Logger logger = LogUtils.getLogger(DoneCommand.class);
    public static final String MESSAGE_USAGE = "done\n\t"
        + "Format: done /id TASK_ID /status Y|N\n\n\t"
        + "Note: content in CAP: need to be fulfilled by the user\n\n"
        + "Y is for marking a task as done, N for unmarking a task\n\n"
        + "This command will mark a task as done\n"
        + "Examples:\n\t"
        + "done /id 1 /status Y\n\t\t"
        + "mark the task with id 1 as done";

    public static final String MESSAGE_INVALID_INPUT = "Error: Invalid status input!";
    public static final String MESSAGE_INVALID_ID = "Error: Id input does not exist!";

    public static final String COMMAND_PREFIX = "Noted. I have mark the below task as done: \n";
    public static final String COMMAND_PREFIX1 = "Whoops. You already have marked the tasked below as done: \n";
    public static final String COMMAND_PREFIX2 = "Noted. I have mark the below task as not done: \n";
    public static final String COMMAND_PREFIX3 = "Whoops. This tasks is still not completed done: \n";
    private int taskID;
    private String status;

    public DoneCommand(int taskID, String status) {
        this.taskID = taskID;
        this.status = status;
    }

    @Override
    public CommandResult commandExecute(TaskList taskList) throws CommandException {
        logger.info("Attempting to execute for done command");
        Task task;

        try {
            task = taskList.getTaskById(taskID);
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_INVALID_ID);
        }

        if (status.equalsIgnoreCase("y")) {
            if (task.isDone) {
                return new CommandResult(COMMAND_PREFIX1.concat(task.toString()), false);
            } else {
                task.markAsDone();
                logger.info("Successfully executed done command");
                return new CommandResult(COMMAND_PREFIX.concat(task.toString()), true);
            }
        } else if (status.equalsIgnoreCase("n")) {
            if (task.isDone) {
                task.markAsNotDone();
                logger.info("Successfully executed done command");
                return new CommandResult(COMMAND_PREFIX2.concat(task.toString()), true);
            }else{
                return new CommandResult(COMMAND_PREFIX3.concat(task.toString()), false);
            }

        } else {
            throw new CommandException(MESSAGE_INVALID_INPUT);
        }

    }
}