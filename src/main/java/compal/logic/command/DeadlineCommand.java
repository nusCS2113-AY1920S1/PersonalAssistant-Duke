package compal.logic.command;

import compal.commons.CompalUtils;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Deadline;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.ArrayList;
import java.util.Date;

/**
 * Add a deadline type task.
 */
public class DeadlineCommand extends Command {

    private static final String MESSAGE_GREETING = "The following tasks were added: \n";
    private String description;
    private ArrayList<String> startDateList;
    private Task.Priority priority;
    private String endTime;
    private String finalDateString;

    /**
     * This is the constructor.
     *
     * @param description   description of deadline.
     * @param priority      priority of deadline.
     * @param startDateList date of deadline.
     * @param endTime       end time of deadline.
     */
    public DeadlineCommand(String description, Task.Priority priority, ArrayList<String> startDateList,
                           String endTime, String finalDateString) {
        this.description = description;
        this.priority = priority;
        this.startDateList = startDateList;
        this.endTime = endTime;
        this.finalDateString = finalDateString;
    }

    @Override
    public CommandResult commandExecute(TaskList taskList) {
        String finalList = MESSAGE_GREETING;
        Date finalDate = CompalUtils.stringToDate(finalDateString);
        for (String startDateString : startDateList) {
            Date startDate = CompalUtils.stringToDate(startDateString);
            while (!startDate.after(finalDate)) {
                startDateString = CompalUtils.dateToString(startDate);
                Deadline indivDeadline = new Deadline(description, priority, startDateString, endTime);
                taskList.addTask(indivDeadline);
                finalList += indivDeadline.toString();
                startDate = incrementDateByWeek(startDate);
            }
        }
        return new CommandResult(finalList, true);
    }
}
