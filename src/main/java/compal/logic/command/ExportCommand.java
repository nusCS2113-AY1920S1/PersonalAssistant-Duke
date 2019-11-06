package compal.logic.command;


import compal.commons.LogUtils;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Action;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;


public class ExportCommand extends Command {
    public static final String MESSAGE_USAGE = "export\n\t"
        + "Format: export /file-name <name of file>\n\n\t"
        + "Note: content in \"<>\": need to be fulfilled by the user\n\n"
        + "This command will export COMPal current schedule to an ics file\n"
        + "Examples:\n\t"
        + "export /file-name cal\n\t\t"
        + "export COMPal schedule to cal.ics.";
    public static final String MESSAGE_UNABLE_CREATE_FILE = "Error: Unable to create file! Try again!";
    public static final String MESSAGE_UNABLE_CREATE_CAL = "Error: Unable to output to calender!";
    public static final String MESSAGE_SUCCESS = "Your COMPal schedule has been successfully exported!\n";
    public static final String MESSAGE_EMPTY_LIST = "Looks like your schedule is empty and theres nothing to export!";

    private String fileName;
    private static final Logger logger = LogUtils.getLogger(ExportCommand.class);

    /**
     * Construct the ExportCommand class.
     *
     * @param fileName the file to be exported to
     */
    public ExportCommand(String fileName) {
        this.fileName = fileName.concat(".ics");
    }


    @Override
    public CommandResult commandExecute(TaskList taskList) throws CommandException {
        logger.info("Attempting to execute export command");
        ArrayList<Task> toList = taskList.getArrList();
        if (toList.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST);
        }

        Calendar calendar = createIcsCal(taskList);

        FileOutputStream fileOutput;
        try {
            fileOutput = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            throw new CommandException(MESSAGE_UNABLE_CREATE_FILE);
        }

        CalendarOutputter calendarOutputter = new CalendarOutputter();
        try {
            calendarOutputter.output(calendar, fileOutput);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_UNABLE_CREATE_CAL);
        }

        logger.info("Successfully executed export command");
        return new CommandResult(MESSAGE_SUCCESS.concat("Your file is saved to " + fileName), false);
    }

    /**
     * Create ics calender object.
     *
     * @param taskList the current tasklist
     * @return ics calendar
     */
    private static Calendar createIcsCal(TaskList taskList) {

        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        ArrayList<Task> toList = taskList.getArrList();
        for (Task t : toList) {
            VEvent event = new VEvent();
            event.getProperties().add(new Summary(t.getDescription()));
            event.getProperties().add(new Description(" Priority:" + t.getPriority()));
            final DtStart dtStart;
            if ("E".equals(t.getSymbol())) {
                dtStart = new DtStart(new DateTime(t.getStartTime()));
            } else {
                dtStart = new DtStart(new DateTime(t.getEndTime()));
            }

            event.getProperties().add(dtStart);

            final DtEnd dtEnd = new DtEnd(new DateTime(t.getEndTime()));

            event.getProperties().add(dtEnd);
            UidGenerator ug = new RandomUidGenerator();
            Uid uid = ug.generateUid();
            event.getProperties().add(uid);

            if (t.hasReminder()) {
                VAlarm reminder;
                if (t.getSymbol().equals("E")) {
                    reminder = new VAlarm(new DateTime(t.getStartTime()));
                } else {
                    reminder = new VAlarm(new DateTime(t.getEndTime()));
                }
                reminder.getProperties(t.getDescription());
                reminder.getProperties().add(Action.DISPLAY);
                reminder.getProperties().add(new Description(t.getDescription()));
                event.getAlarms().add(reminder);
            }
            calendar.getComponents().add(event);

        }
        return calendar;
    }
}
