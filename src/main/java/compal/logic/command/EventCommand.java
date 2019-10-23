package compal.logic.command;

import compal.commons.CompalUtils;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.ArrayList;
import java.util.Date;

//@@author yueyeah
/**
 * A command object handles the creation of events, and adds them to the tasklist.
 * Contains functionality to add multiple recurring events.
 */
public class EventCommand extends Command {

    public static final String MESSAGE_USAGE = "event\n\t"
            + "Format: event <description> /date dd/mm/yyyy... /start hhhh /end hhhh "
            + "[/priority low|medium|high] [/final-date dd/mm/yyyy]\n\n\t"
            + "Note: content in \"[]\": optional\n\t"
            + "content in \"<>\": need to be fulfilled by the user\n\t"
            + "content separated by \"|\": must choose exactly one from them\n\t"
            + "\"...\" means you can add multiple. e.g. dd/mm/yyyy... means you can add 01/01/2019 02/01/2019\n\t"
            + "dd/mm/yyyy is the date format. e.g. 01/01/2000\n\t"
            + "hhhh is the time format. e.g. 1740\n\n"
            + "This command will add a task which has a date, start time and end time\n"
            + "Examples:\n\t"
            + "event cs2106 meeting /date 01/01/2019 /start 0800 /end 1000\n\t\t"
            + "add a task which last from 01/01/2019 8am to 10am with default priority low\n"
            + "event cs2106 meeting /date 01/01/2019 /start 0800 /end 0600\n\t\t"
            + "add a task which last from 01/01/2019 8am to 02/01/2019 6am with default priority low\n"
            + "event dinner /date 01/01/2019 02/01/2019 /start 1730 /end 1800 /final-date 10/01/2019\n\t\t"
            + "add a task which happens on 01/01/2019 and 02/01/2019 5:30pm to 6pm and repeat weekly until 10/01/2019\n"
            + "event cs2106as /date 01/01/2019 /start 0800 /end 1000 /priority high\n\t\t"
            + "add a task which last from 01/01/2019 8am to 10am with default priority high";
    private static final String MESSAGE_GREETING = "The following tasks were added: \n";
    private static final String DAY_END = "2359";
    private static final String DAY_START = "0000";
    private static final int DEFAULT_WEEK_INTERVAL = 7;
    private static final int DEFAULT_DAY_INTERVAL = 1;

    private String description;
    private ArrayList<String> startDateList;
    private Task.Priority priority;
    private String startTime;
    private String endTime;
    private String finalDateString;

    /**
     * Constructor for the Event Command object.
     *
     * @param description     Description of the event
     * @param startDateList   List of dates when the event will start
     * @param priority        Priority assigned to the event
     * @param startTime       Start time of the event
     * @param endTime         End time of the event
     * @param finalDateString Final possible date that the event will occur
     */
    public EventCommand(String description, ArrayList<String> startDateList, Task.Priority priority, String startTime,
                        String endTime, String finalDateString) {
        this.description = description;
        this.startDateList = startDateList;
        this.priority = priority;
        this.startTime = startTime;
        this.endTime = endTime;
        this.finalDateString = finalDateString;
    }

    @Override
    public CommandResult commandExecute(TaskList taskList) throws CommandException {
        boolean isInOneDay = CompalUtils.isTimeInSequence(startTime, endTime);
        String finalList = MESSAGE_GREETING;
        Date finalDate = CompalUtils.stringToDate(finalDateString);
        for (String startDateString : startDateList) {
            Date startDate = CompalUtils.stringToDate(startDateString);
            while (!startDate.after(finalDate)) {
                String eventAdditionString = createAndAddEvent(isInOneDay, taskList, description, startDate,
                        priority, startTime, endTime);
                finalList += eventAdditionString;
                startDate = incrementDateByDays(startDate, DEFAULT_WEEK_INTERVAL);
            }
        }
        return new CommandResult(finalList, true);
    }

    //@@author yueyeah
    /**
     * Handles the creation of Event object, and addition of Event object into TaskList.
     * Abstracts out the handling of events that drag over the end of the first day into the start of second day.
     *
     * @param isInOneDay True if duration of event is within one day, False if drags on to next day.
     * @param taskList The list of tasks, where the event will be added to.
     * @param description The description of the task.
     * @param startDate The start date of the task, in the form of a Date object.
     * @param priority The priority of the task.
     * @param startTime The start time of the task, in the form of a String object.
     * @param endTime The end time of the task, in the form of a String object.
     * @return The string output of the addition of the task, to be returned to logic manager as a command result.
     */
    public String createAndAddEvent(boolean isInOneDay, TaskList taskList, String description, Date startDate,
                                    Task.Priority priority, String startTime, String endTime) {
        String startDateString = CompalUtils.dateToString(startDate);
        if (isInOneDay) {
            Event indivEvent = new Event(description, priority, startDateString, startTime, endTime);
            taskList.addTask(indivEvent);
            return indivEvent.toString();
        } else {
            Event firstEventPart = new Event(description, priority, startDateString, startTime, DAY_END);
            taskList.addTask(firstEventPart);
            Date nextDate = incrementDateByDays(startDate, DEFAULT_DAY_INTERVAL);
            String nextDateString = CompalUtils.dateToString(nextDate);
            Event secondEventPart = new Event(description, priority, nextDateString, DAY_START, endTime);
            taskList.addTask(secondEventPart);
            String wholeEventString = firstEventPart.toString() + secondEventPart.toString();
            return wholeEventString;
        }
    }
}
