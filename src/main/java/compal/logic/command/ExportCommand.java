package compal.logic.command;


import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class ExportCommand extends Command {

    private String ICAL_FILE_NAME;

    /**
     * Construct the ExportCommand class.
     *
     * @param fileName the file to be exported to
     */
    public ExportCommand(String fileName) {
        this.ICAL_FILE_NAME = fileName.concat(".ics");
    }


    @Override
    public CommandResult commandExecute(TaskList taskList) throws CommandException {


        Calendar calendar = createCalendar(taskList);
        System.out.println(calendar);


        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream("mycalendar.ics");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        CalendarOutputter outputter = new CalendarOutputter();
        try {
            outputter.output(calendar, fout);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new CommandResult("HI", false);
    }

    public static Calendar createCalendar(TaskList taskList) {
        ArrayList<Task> toList = taskList.getArrList();
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        for (Task t : toList) {


            VEvent event = new VEvent();
            event.getProperties().add(new Summary(t.getDescription()));
            event.getProperties().add(new Description(t.getDescription() + " [" + t.getPriority() + "]"));
            final DtStart dtStart;
            if (t.getSymbol().equals("E")) {
                dtStart = new DtStart(new net.fortuna.ical4j.model.DateTime(t.getStartTime()));
            } else {
                dtStart = new DtStart(new net.fortuna.ical4j.model.DateTime(t.getEndTime()));
            }
            event.getProperties().add(dtStart);

            final DtEnd dtEnd = new DtEnd(new net.fortuna.ical4j.model.DateTime(t.getEndTime()));

            event.getProperties().add(dtEnd);
            UidGenerator ug = new RandomUidGenerator();
            Uid uid = ug.generateUid();
            event.getProperties().add(uid);
            calendar.getComponents().add(event);

        }
        return calendar;
    }
}
