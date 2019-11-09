package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.CalendarOutput;
import chronologer.storage.Storage;
import chronologer.task.Priority;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.validate.ValidationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;


/**
 * Processes and export the timeline as an ics file.
 *
 * @author Tan Yi Xiang
 * @version v1.8
 */
public class ExportCommand extends Command {

    private static final String BLANK = "";
    private static final String DEADLINE = "DEADLINE";
    private static final String EVENT = "EVENT";
    private static final String TODO_PERIOD = "TODO PERIOD";

    private String fileName;
    private boolean hasDeadlineFlag;
    private boolean hasEventFlag;
    private boolean hasTodoFlag;

    /**
     * Initializes the different parameters for the export command.
     *
     * @param fileName        Name of the file
     * @param hasDeadlineFlag Indication to extract deadline tasks.
     * @param hasEventFlag    Indication to extract event tasks.
     * @param hasTodoFlag     Indication to extract todo with period tasks.
     */
    public ExportCommand(String fileName, Boolean hasDeadlineFlag, Boolean hasEventFlag, Boolean hasTodoFlag) {
        this.fileName = fileName;
        this.hasDeadlineFlag = hasDeadlineFlag;
        this.hasEventFlag = hasEventFlag;
        this.hasTodoFlag = hasTodoFlag;
    }

    /**
     * Convert the task list to a calendar file.
     *
     * @param tasks   Holds the list of all the tasks the user has.
     * @param storage Allows the saving of the file to persistent storage.
     * @throws ChronologerException If the task list is empty.
     * @throws ValidationException  If the calendar is empty.
     */
    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException, ValidationException {
        Calendar calendar = initializeCalendar();
        ArrayList<Task> taskList = tasks.getTasks();
        checkEmptyList(taskList);
        if (hasDeadlineFlag) {
            extractDeadline(taskList, calendar);
        }
        if (hasEventFlag) {
            extractEvent(taskList, calendar);
        }
        if (hasTodoFlag) {
            extractTodoPeriod(taskList, calendar);
        }
        if (hasNoFlags()) {
            extractDeadline(taskList, calendar);
            extractEvent(taskList, calendar);
            extractTodoPeriod(taskList, calendar);
        }

        if (isCalendarValid(calendar)) {
            CalendarOutput.outputCalendar(fileName.trim(), calendar);
        } else {
            UiMessageHandler.outputMessage(ChronologerException.emptyCalendar());
            throw new ChronologerException(ChronologerException.emptyCalendar());
        }
    }

    /**
     * Initializes a Calendar object with default properties.
     *
     * @return An initial calendar to be built upon later.
     */
    private Calendar initializeCalendar() {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Chronologer//iCal4j 1.1//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        return calendar;
    }

    /**
     * Iterates through the task list and convert deadline tasks to calendar components.
     *
     * @param taskList The list of tasks
     * @param calendar The calendar to add the components to.
     */
    private void extractDeadline(ArrayList<Task> taskList, Calendar calendar) {
        for (Task task : taskList) {
            if (isDeadline(task)) {
                VEvent deadline = convertDeadline(task);
                calendar.getComponents().add(deadline);
            }
        }
    }

    /**
     * Iterates through the task list and convert event tasks to calendar components.
     *
     * @param taskList The list of tasks
     * @param calendar The calendar to add the components to.
     */
    private void extractEvent(ArrayList<Task> taskList, Calendar calendar) {
        for (Task task : taskList) {
            if (isEvent(task)) {
                VEvent event = convertEventOrTodoPeriod(task);
                calendar.getComponents().add(event);
            }
        }
    }

    /**
     * Iterates through the task list and convert todo with period tasks to calendar components.
     *
     * @param taskList The list of tasks
     * @param calendar The calendar to add the components to.
     */
    private void extractTodoPeriod(ArrayList<Task> taskList, Calendar calendar) {
        for (Task task : taskList) {
            if (isTodoPeriod(task)) {
                VEvent todoPeriod = convertEventOrTodoPeriod(task);
                calendar.getComponents().add(todoPeriod);
            }
        }
    }

    /**
     * Convert localDateTime date to java Calendar format.
     *
     * @param date The date to be converted
     * @return A Java Calendar containing the converted date.
     */
    private java.util.Calendar convertToCalendar(LocalDateTime date) {
        java.util.Calendar utilCalendar = new GregorianCalendar();
        utilCalendar.set(java.util.Calendar.YEAR, date.getYear());
        utilCalendar.set(java.util.Calendar.MONTH, date.getMonthValue() - 1);
        utilCalendar.set(java.util.Calendar.DAY_OF_MONTH, date.getDayOfMonth());
        utilCalendar.set(java.util.Calendar.HOUR_OF_DAY, date.getHour());
        utilCalendar.set(java.util.Calendar.MINUTE, date.getMinute());
        utilCalendar.set(java.util.Calendar.SECOND, 0);
        return utilCalendar;
    }


    /**
     * Convert deadline tasks into properties supported by ics files.
     *
     * @param deadlineTask The deadline to be converted.
     * @return A VEvent component representing the converted deadline.
     */
    private VEvent convertDeadline(Task deadlineTask) {
        java.util.Calendar deadlineCalendar = convertToCalendar(deadlineTask.getStartDate());
        DateTime deadlineDate = new DateTime(deadlineCalendar.getTime());
        DateTime currentDate = getCurrentDate();
        String title = createTitle(deadlineTask);
        VEvent deadline = new VEvent(currentDate, deadlineDate, title);
        createDescription(deadlineTask, deadline);
        createLocation(deadlineTask, deadline);
        setPriority(deadlineTask, deadline);
        UidGenerator generator = new RandomUidGenerator();
        deadline.getProperties().add(generator.generateUid());
        return deadline;
    }

    /**
     * Convert event or todo with period tasks into properties supported by ics files.
     *
     * @param eventTask The event or todo to be converted.
     * @return A VEvent component representing the converted event or todo.
     */
    private VEvent convertEventOrTodoPeriod(Task eventTask) {
        java.util.Calendar eventStartCalendar = convertToCalendar(eventTask.getStartDate());
        java.util.Calendar eventEndCalendar = convertToCalendar(eventTask.getEndDate());
        DateTime startEventDate = new DateTime(eventStartCalendar.getTime());
        DateTime endEventDate = new DateTime(eventEndCalendar.getTime());
        String title = createTitle(eventTask);
        VEvent event = new VEvent(startEventDate, endEventDate, title);
        createDescription(eventTask, event);
        createLocation(eventTask, event);
        setPriority(eventTask, event);
        UidGenerator generator = new RandomUidGenerator();
        event.getProperties().add(generator.generateUid());
        return event;
    }

    /**
     * Obtain the current system time date.
     *
     * @return DateTime object representing the current system time.
     */
    private DateTime getCurrentDate() {
        LocalDateTime currentDate = LocalDateTime.now();
        java.util.Calendar currentCalendar = convertToCalendar(currentDate);
        return new DateTime(currentCalendar.getTime());
    }

    /**
     * Convert task description to title string to be used in the ics file.
     *
     * @param task The task to have its description converted.
     * @return The newly created title of the task.
     */
    private String createTitle(Task task) {
        if (BLANK.equals(task.getModCode())) {
            return task.getDescription();
        } else {
            return task.getModCode() + ": " + task.getDescription();
        }
    }

    /**
     * Convert the task comments if any to ics description component.
     *
     * @param task  The task to have its comments converted.
     * @param event The VEvent component to add a description to.
     */
    private void createDescription(Task task, VEvent event) {
        if (!BLANK.equals(task.getComment())) {
            event.getProperties().add(new Description(task.getComment()));
        }

    }

    /**
     * Convert the task location if any to ics location component.
     *
     * @param task  The task to have its location converted.
     * @param event The VEvent component to add a location to.
     */
    private void createLocation(Task task, VEvent event) {
        if (!BLANK.equals(task.getLocation())) {
            event.getProperties().add(new Location(task.getLocation()));
        }
    }

    /**
     * Convert the task location if any to ics priority component.
     *
     * @param task  The task to have its priority converted.
     * @param event The VEvent component to add a priority to.
     */
    private void setPriority(Task task, VEvent event) {
        if (task.getPriority() == Priority.HIGH) {
            event.getProperties().add(net.fortuna.ical4j.model.property.Priority.HIGH);
        } else if (task.getPriority() == Priority.MEDIUM) {
            event.getProperties().add(net.fortuna.ical4j.model.property.Priority.MEDIUM);
        } else {
            event.getProperties().add(net.fortuna.ical4j.model.property.Priority.LOW);
        }
    }

    /**
     * Checks whether the task list is empty.
     *
     * @param taskList The task list to be checked
     * @throws ChronologerException If the task list is empty and stop the export command.
     */
    private void checkEmptyList(ArrayList<Task> taskList) throws ChronologerException {
        if (taskList.size() == 0) {
            UiMessageHandler.outputMessage(ChronologerException.emptyExport());
            throw new ChronologerException(ChronologerException.emptyExport());
        }
    }

    /**
     * Check whether the Calendar is valid.
     *
     * @param calendar The calendar to be validated.
     * @return False if the calendar is not valid eg: Empty etc.
     */
    private boolean isCalendarValid(Calendar calendar) {
        try {
            calendar.validate(true);
            return true;
        } catch (ValidationException e) {
            return false;
        }
    }

    /**
     * Check whether the export command has no flags enabled which indicates an export all command.
     *
     * @return True if there's no flags enabled.
     */
    private boolean hasNoFlags() {
        return (!hasDeadlineFlag && !hasEventFlag && !hasTodoFlag);
    }

    /**
     * Check whether the task is of deadline type.
     *
     * @return True if the task is a deadline.
     */
    private boolean isDeadline(Task task) {
        return (DEADLINE.equals(task.getType()));
    }

    /**
     * Check whether the task is of event type.
     *
     * @return True if the task is an event.
     */
    private boolean isEvent(Task task) {
        return (EVENT.equals(task.getType()));
    }

    /**
     * Check whether the task is of todo period type.
     *
     * @return True if the task is a todo period.
     */
    private boolean isTodoPeriod(Task task) {
        return (TODO_PERIOD.equals(task.getType()));
    }

}
