package compal.logic.command;

import compal.commons.CompalUtils;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.ArrayList;
import java.util.Date;

public class EventCommand extends Command {

    private static final String MESSAGE_GREETING = "The following tasks were added: \n";
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
        String finalList = MESSAGE_GREETING;
        Date finalDate = CompalUtils.stringToDate(finalDateString);
        for (String startDateString : startDateList) {
            Date startDate = CompalUtils.stringToDate(startDateString);
            while (!startDate.after(finalDate)) {
                startDateString = CompalUtils.dateToString(startDate);
                Event indivEvent = new Event(description, priority, startDateString, startTime, endTime);
                finalList += indivEvent.toString();
                taskList.addTask(indivEvent);
                startDate = incrementDateByWeek(startDate);
            }
        }
        return new CommandResult(finalList, true);
    }
}
