package commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import tasks.Deadline;
import tasks.Events;
import tasks.FixedDuration;
import tasks.Periods;
import tasks.MultipleEvent;
import tasks.ToDos;
import tasks.Task;
import tasks.TaskList;
import controlpanel.DukeException;
import controlpanel.Storage;
import controlpanel.Ui;
import javafx.util.Pair;

/**
 * The command which aims to add a new task to the list.
 */
public class AddCommand extends Command {

    private String type;
    private String inputString;
    private SimpleDateFormat simpleDateFormat;


    /**
     * The constructor to initialize a add command object.
     * @param string The type of the command.
     * @param cmd The content of the original text in input command.
     */
    public AddCommand(String string, String cmd) {
        type = string;
        inputString = cmd;
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
    }

    /**
     * This method labels whether this command means ceasing the overall program.
     * @return Whether this command means ceasing the overall program.
     */
    @Override
    public boolean isExit() {
        return false;
    }


    /**
     * The method execute the add command to add different types of task to the task list based on the.
     * task type which is specified in the input command.
     * @param tasks The task list object to interact with the checklist.
     * @param ui To print something needed in user interface.
     * @param storage To re-save the data in local disk if necessary.
     * @throws ParseException When there is something wrong with the parsing.
     * @throws DukeException When the command line is not qualified.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ParseException, DukeException {
        switch (type) {
        case "period": {
            String[] getDate1 = inputString.split("/from ");
            String[] getDate2 = getDate1[1].split("/to ");
            Date date1 = simpleDateFormat.parse(getDate2[0]);
            Date date2 = simpleDateFormat.parse(getDate2[1]);
            if (invalidDuration(date1, date2)) {
                throw new DukeException("OOPS!!! The period of this event is invalid.");
            }
            String formattedDate1 = simpleDateFormat.format(date1);
            String formattedDate2 = simpleDateFormat.format(date2);
            Task t = new Periods(getDate1[0].replaceFirst("period ", ""),
                    formattedDate1, formattedDate2);
            tasks.addTask(t);
            break;
        }
        case "deadline": {
            String[] getDate = inputString.split("/by ");
            Date date = simpleDateFormat.parse(getDate[getDate.length - 1]);
            String formattedDate = simpleDateFormat.format(date);
            Task t = new Deadline(getDate[0].replaceFirst("deadline ", ""),
                    date);
            tasks.addTask(t);
            break;
        }
        case "event": {
            if (inputString.equals("event")) {
                throw new DukeException("OOPS!!! The description of a event cannot be empty.");
            }
            String[] getDate = inputString.split("/at ");
            String eventPeriod = getDate[getDate.length - 1];
            String[] startendDate = eventPeriod.split(" to ");
            Date startDate = simpleDateFormat.parse(startendDate[0]);
            Date endDate = simpleDateFormat.parse(startendDate[1]);
            if (invalidDuration(startDate, endDate)) {
                throw new DukeException("OOPS!!! The period of this event is invalid.");
            }
            Task t = new Events(getDate[0].replaceFirst("event ", ""),
                    startDate, endDate);
            if (scheduleClashes(tasks, (Events) t)) {
                throw new DukeException("OOPS! There seems to be a clash in your schedule.");
            }
            tasks.addTask(t);
            break;
        }
        case "todo": {
            if (inputString.equals("todo")) {
                throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
            }
            Task t = new ToDos(inputString.replaceFirst("todo ", ""));
            tasks.addTask(t);
            break;
        }
        case "duration": {
            if (inputString.equals("duration")) {
                throw new DukeException("OOPS!!! The description of a duration cannot be empty.");
            }
            String[] getDuration = inputString.split("/needs ");
            Task t = new FixedDuration(getDuration[0].replaceFirst("duration ", ""), getDuration[1]);
            tasks.addTask(t);
            break;
        }
        case "multiEvent": {
            if (inputString.equals("multiEvent")) {
                throw new DukeException("OOPS!!! The description of a multiple event cannot be empty.");
            }
            String[] getDate = inputString.split("/at");
            String description = getDate[0].replaceFirst("multiEvent ", "");
            String[] dateStr = getDate[1].split("/or");
            ArrayList<Pair<Date, Date>> dates = new ArrayList<>();
            for (String choices : dateStr) {
                String[] startEndDate = choices.split("to ");
                Date startDate = simpleDateFormat.parse(startEndDate[0]);
                Date endDate = simpleDateFormat.parse(startEndDate[1]);
                if (invalidDuration(startDate, endDate)) {
                    throw new DukeException("OOPS!!! The period of this event is invalid.");
                }
                Pair<Date, Date> tempDate = new Pair<>(startDate, endDate);
                dates.add(tempDate);
            }
            Task t = new MultipleEvent(description, dates);
            tasks.addTask(t);
            break;
        }
        default:
            break;
        }
        storage.writeTheFile(tasks.getCheckList());
        ui.appendToOutput(" Got it. I've added this task: \n");
        ui.appendToOutput("     " + tasks.getTask(tasks.lengthOfList() - 1).toString() + "\n");
        ui.appendToOutput(" Now you have " + tasks.lengthOfList() + " tasks in the list.\n");
    }

    /**
     * This method check that is there any time clash between the input event and.
     * the existing tasks.
     * @param tasks The task list object to interact with the checklist.
     * @param e The input event needs to be checked.
     * @return Is there any time clash between the input event an the existing tasks.
     */
    public Boolean scheduleClashes(TaskList tasks, Events e) {
        for (Task t : tasks.getCheckList()) {
            if (t instanceof Events) {
                if (e.getStartDateAt().compareTo(((Events) t).getEndDateAt()) <= 0
                    && e.getEndDateAt().compareTo(((Events) t).getStartDateAt()) >= 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method compares the start date and the end date of one event.
     * to check whether it is a valid input duration.
     * @param from The start date of a event.
     * @param to The end date of a event.
     * @return Whether it is a valid input duration.
     */
    public Boolean invalidDuration(Date from, Date to) {
        return (from.compareTo(to) > 0);
    }
}
