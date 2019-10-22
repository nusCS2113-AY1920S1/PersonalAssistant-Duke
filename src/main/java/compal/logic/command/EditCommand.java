package compal.logic.command;

import compal.commons.CompalUtils;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.Date;

//@@author jaedonkey
public class EditCommand extends Command {
    private String description;
    private Date date;
    private String startTime;
    private String endTime;
    private int taskId;
    private Task.Priority priority;

    //@@author jaedonkey
    /**
     * Constructs an edit command object.
     * @param taskId id of task to edit
     * @param description new description
     * @param date new date
     * @param startTime new start time
     * @param endTime new end time
     * @param priority new priority level
     */
    public EditCommand(int taskId, String description, Date date, String startTime, String endTime,
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
        if (startTime != null && !toEdit.getSymbol().equalsIgnoreCase("D")) { //dealine has no start time
            toEdit.setStartTime(startTime);
        }
        if (endTime != null) {
            toEdit.setEndTime(endTime);
        }
        if (priority != null) {
            toEdit.setPriority(priority);
        }

        return new CommandResult("Your task has been edited:\n" + toEdit.toString(), true);
    }



}
