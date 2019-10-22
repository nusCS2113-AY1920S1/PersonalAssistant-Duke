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

    public static final String MESSAGE_USAGE = "deadline\n\t"
            + "Format: deadline <description> /date dd/mm/yyyy... /end hhhh "
            + "[/priority low|medium|high] [/final-date dd/mm/yyyy]\n\n\t"
            + "Note: content in \"[]\": optional\n\t"
            + "content in \"<>\": need to be fulfilled by the user\n\t"
            + "content separated by \"|\": must choose exactly one from them\n\t"
            + "\"...\" means you can add multiple. e.g. dd/mm/yyyy... means you can add 01/01/2019 02/01/2019\n\t"
            + "dd/mm/yyyy is the date format. e.g. 01/01/2000\n\t"
            + "hhhh is the time format. e.g. 1740\n\n"
            + "This command will add a task which has a deadline date and time\n"
            + "Examples:\n\t"
            + "deadline cs2106as /date 01/01/2019 /end 1000\n\t\t"
            + "add a task which ends at 01/01/2019 10:00am with default priority low\n"
            + "deadline dinner /date 01/01/2019 02/01/2019 /end 1800 /final-date 10/01/2019\n\t\t"
            + "add a task which ends on 01/01/2019 and 02/01/2019 6pm and repeat weekly until 10/01/2019\n"
            + "deadline cs2106as /date 01/01/2019 /end 1000 /priority high\n\t\t"
            + "dd a task which ends at 01/01/2019 10:00am with priority high";
    private static final String MESSAGE_GREETING = "The following tasks were added: \n";
    private static final int DEFAULT_WEEK_INTERVAL = 7;
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
                startDate = incrementDateByDays(startDate, DEFAULT_WEEK_INTERVAL);
            }
        }
        return new CommandResult(finalList, true);
    }
}
