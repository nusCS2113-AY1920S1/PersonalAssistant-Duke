package compal.logic.command;

import compal.commons.CompalUtils;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.Date;

public class EditCommand extends Command {
    private String description;
    private Date date;
    private Date startTime;
    private Date endTime;
    private int taskId;
    private Task.Priority priority;


    public EditCommand(int taskId, String description, Date date, Date startTime, Date endTime,
                       Task.Priority priority) {
        this.taskId = taskId;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
    }


    @Override
    public CommandResult commandExecute(TaskList taskList) throws CommandException {
        Task toEdit = taskList.getTaskById(taskId);
        if (description != null) {
            toEdit.setDescription(description);
        }
        if (date != null) {
            toEdit.setDate(CompalUtils.dateToString(date));
        }
        if (startTime != null) {
            toEdit.setStartTime(CompalUtils.dateToString(startTime));
        }
        if (endTime != null) {
            toEdit.setEndTime(CompalUtils.dateToString(endTime));
        }
        if (priority != null) {
            toEdit.setPriority(priority);
        }

        return new CommandResult("Your task has been edited:\n" + toEdit.toString(), true);
    }



}
