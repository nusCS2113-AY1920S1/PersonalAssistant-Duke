package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.CalendarOutput;
import chronologer.storage.Storage;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.task.Todo;
import chronologer.ui.UiTemporary;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;


/**
 * Processes and export the timeline as an ics file.
 *
 * @author Tan Yi Xiang
 * @version v1.4
 */
public class ExportCommand extends Command {

    private static final String DEADLINE = "DEADLINE";
    private static final String EVENT = "EVENT";
    private static final String TODO_PERIOD = "TODO PERIOD";
    private String fileName;

    public ExportCommand(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {

        Calendar calendar = initializeCalendar();
        ArrayList<Task> taskList = tasks.getTasks();
        for (Task task : taskList) {
            if (isDeadline(task)) {
                VEvent deadline = convertDeadline(task);
                calendar.getComponents().add(deadline);
            } else if (isEvent(task) || isTodoPeriod(task)) {
                VEvent event = convertEventOrTodoPeriod(task);
                calendar.getComponents().add(event);
            }
        }
        CalendarOutput.outputCalendar(fileName, calendar);
    }

    private Calendar initializeCalendar() {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Chronologer//iCal4j 1.1//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        return calendar;
    }

    private java.util.Calendar convertToCalendar(LocalDateTime startDate) {
        java.util.Calendar utilCalendar = new GregorianCalendar();
        utilCalendar.set(java.util.Calendar.YEAR, startDate.getYear());
        utilCalendar.set(java.util.Calendar.MONTH, startDate.getMonthValue() - 1);
        utilCalendar.set(java.util.Calendar.DAY_OF_MONTH, startDate.getDayOfMonth());
        utilCalendar.set(java.util.Calendar.HOUR_OF_DAY, startDate.getHour());
        utilCalendar.set(java.util.Calendar.MINUTE, startDate.getMinute());
        utilCalendar.set(java.util.Calendar.SECOND, 0);
        return utilCalendar;
    }

    private VEvent convertDeadline(Task task) {
        java.util.Calendar deadlineCalendar = convertToCalendar(task.getStartDate());
        DateTime deadlineDate = new DateTime(deadlineCalendar.getTime());
        VEvent deadline = new VEvent(deadlineDate, task.getDescription());
        createDescription(task, deadline);
        createLocation(task, deadline);
        UidGenerator generator = new RandomUidGenerator();
        deadline.getProperties().add(generator.generateUid());
        return deadline;
    }

    private VEvent convertEventOrTodoPeriod(Task task) {
        java.util.Calendar eventStartCalendar = convertToCalendar(task.getStartDate());
        java.util.Calendar eventEndCalendar = convertToCalendar(task.getEndDate());
        DateTime startEventDate = new DateTime(eventStartCalendar.getTime());
        DateTime endEventDate = new DateTime(eventEndCalendar.getTime());
        VEvent event = new VEvent(startEventDate, endEventDate, task.getDescription());
        createDescription(task, event);
        createLocation(task, event);
        UidGenerator generator = new RandomUidGenerator();
        event.getProperties().add(generator.generateUid());
        return event;
    }

    private void createDescription(Task task, VEvent event) {
        if (task.getComment() != null) {
            event.getProperties().add(new Description(task.getComment()));
        }

    }

    private void createLocation(Task task, VEvent event) {
        if (task.getLocation() != null) {
            event.getProperties().add(new Location(task.getLocation()));
        }
    }

    private boolean isDeadline(Task task) {
        return (DEADLINE.equals(task.getType()));
    }

    private boolean isEvent(Task task) {
        return (EVENT.equals(task.getType()));
    }

    private boolean isTodoPeriod(Task task) {
        return (TODO_PERIOD.equals(task.getType()));
    }
}
