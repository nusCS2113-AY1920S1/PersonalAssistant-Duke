package compal.logic.command;

import compal.commons.CompalUtils;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.Date;

//@@author jaedonkey
public class EditCommand extends Command {

    public static final String MESSAGE_USAGE = "edit\n\t"
            + "Format: edit /id <num> "
            + "[/description <description>][/date dd/mm/yyyy][/start hhhh][/end hhhh][/priority high|medium|low]\n\n\t"
            + "Note: content in \"[]\": optional\n\t"
            + "content in \"<>\": need to be fulfilled by the user\n\t"
            + "content separated by \"|\": must choose exactly one from them\n\t"
            + "dd/mm/yyyy is the date format. e.g. 01/01/2000\n\t"
            + "hhhh is the time format. e.g. 1740\n\n"
            + "This command will edit a task\n"
            + "Examples:\n\t"
            + "edit /id 0 /description sleep /date 01/01/2019 /start 2300 /end 1200 /priority high\n\t\t"
            + "edit the task with id 0 to have description sleep on 01/01/2019 from 11pm till 12am of the next day";
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
