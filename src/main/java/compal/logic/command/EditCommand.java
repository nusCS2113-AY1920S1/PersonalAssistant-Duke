package compal.logic.command;

import compal.commons.CompalUtils;
import compal.commons.LogUtils;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.Date;
import java.util.logging.Logger;

//@@author jaedonkey
public class EditCommand extends Command {

    public static final String MESSAGE_USAGE = "edit\n\t"
            + "Format: edit /id <num> "
            + "[/description <description>][/date <dd/mm/yyyy>][/start <hhhh>][/end <hhhh>]"
            + "[/priority <high|medium|low>]\n\n\t"
            + "Note: content in \"[]\": optional\n\t"
            + "You can switch the order of any two blocks (a block starts with \"/\" and ends by the next block)\n\t"
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
    private static final Logger logger = LogUtils.getLogger(EditCommand.class);
    private static final String deadlineStartDateMsg = "Deadline has no start time! Please omit /start <date>!";
    private static final String invalidTaskIdMsg = "Task ID invalid! Please try again!";
    private static final String noValidEditsMsg = "No valid editable fields found! Please include one of the "
            + "following:"
            + "/description , /date . /start, /end , /priority";

    //@@author jaedonkey

    /**
     * Constructs an edit command object.
     *
     * @param taskId      id of task to edit
     * @param description new description
     * @param date        new date
     * @param startTime   new start time
     * @param endTime     new end time
     * @param priority    new priority level
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
        logger.info("Executing edit command");
        Task toEdit;
        try {
            toEdit = taskList.getTaskById(taskId);
        } catch (NullPointerException e) {
            throw new CommandException(invalidTaskIdMsg);
        }


        if (description == null && date == null && startTime == null && endTime == null && priority == null) {
            throw new CommandException(noValidEditsMsg);
        }


        if (description != null) {
            toEdit.setDescription(description);
        }
        if (date != null) {
            toEdit.setMainDate(CompalUtils.dateToString(date));
        }
        if (startTime != null) { //dealine has no start time
            if (!toEdit.getSymbol().equalsIgnoreCase("D")) {
                toEdit.setStartTime(startTime);
            } else {
                throw new CommandException(deadlineStartDateMsg);
            }

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
