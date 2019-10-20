package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Deadline;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

/**
 * Add a deadline type task.
 */
public class DeadlineCommand extends Command {

    private String description;
    private Task.Priority priority;
    private String date;
    private String endTime;

    /**
     * This is the constructor.
     *
     * @param description description of deadline.
     * @param priority priority of deadline.
     * @param date date of deadline.
     * @param endTime end time of deadline.
     */
    public DeadlineCommand(String description, Task.Priority priority, String date, String endTime) {
        this.description = description;
        this.priority = priority;
        this.date = date;
        this.endTime = endTime;
    }

    @Override
    public CommandResult commandExecute(TaskList task) throws CommandException {
        Deadline deadline = new Deadline(description, priority, date, endTime);
        task.addTask(deadline);
        return new CommandResult(deadline.toString(),true);
    }
}
