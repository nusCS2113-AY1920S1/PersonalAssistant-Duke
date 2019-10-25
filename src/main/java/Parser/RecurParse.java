package Parser;

import Commands.AddCommand;
import Commands.Command;
import Commands.RecurringCommand;
import DukeExceptions.DukeException;
import Interface.*;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.TaskList;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
public class RecurParse extends Parse {
    private static String[] split;
    private static String[] split1;
    private static String[] split2;
    private static String[] split3;
    private static String[] split4;
    private static String fullCommand;
    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());
    private static LookupTable LT;

    static {
        try {
            LT = new LookupTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public RecurParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command execute() throws Exception {
        try {
            String activity = fullCommand.trim().substring(7);
            String startWeekDate;
            String endWeekDate;
            split = activity.split("/start"); //split[0] is " module_code description", split[1] is "date to date from time to time"
            if (split[0].trim().isEmpty()) {
                throw new DukeException("\u2639" + " OOPS!!! The description of a event cannot be empty.");
            }
            split1 = split[1].split("/from"); //split1[0] is "date to date" or "week X mon to week X mon", split1[1] is "time to time"
            split3 = split1[0].split("/to"); //split3[0] is (start) "date", split3[1] is (end) "date"
            split4 = split3[0].trim().split(" "); //split the start date
            //recess week mon / week 3 mon / exam week mon / reading week tue
            startWeekDate = split4[0].trim();
            if (startWeekDate.equalsIgnoreCase("reading") || startWeekDate.equalsIgnoreCase("exam")
                    || startWeekDate.equalsIgnoreCase("week") || startWeekDate.equalsIgnoreCase("recess")) {
                startWeekDate = LT.getValue(split3[0].trim());
            } else {
                startWeekDate = split3[0].trim();
            }
            split4 = split3[1].trim().split(" "); //split the end date
            endWeekDate = split4[0].trim();
            if (endWeekDate.equalsIgnoreCase("reading") || endWeekDate.equalsIgnoreCase("exam")
                    || endWeekDate.equalsIgnoreCase("week") || endWeekDate.equalsIgnoreCase("recess")) {
                endWeekDate = LT.getValue(split3[1].trim());
            } else {
                endWeekDate = split3[1].trim();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //format date
            Date startDate = formatter.parse(startWeekDate);
            Date endDate = formatter.parse(endWeekDate);
            split2 = split1[1].split("/to"); //split2[0] is (start) "time", split2[1] is (end) "time"
            SimpleDateFormat formatter1 = new SimpleDateFormat("HHmm"); //format time
            Date startTime = formatter1.parse(split2[0].trim());
            Date endTime = formatter1.parse(split2[1].trim());
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
            String startTimeString = timeFormat.format(startTime);
            String endTimeString = timeFormat.format(endTime);
            return new RecurringCommand(split[0].trim(),startDate, endDate, startTimeString, endTimeString);
        } catch (ParseException | ArrayIndexOutOfBoundsException e) {
            LOGGER.log(Level.INFO, e.toString(), e);
            throw new DukeException("OOPS!!! Please enter recurring event as follows:\n" +
                    "recur/e modCode name_of_event /start dd/MM/yyyy to dd/MM/yyyy /from HHmm /to HHmm\n" +
                    "For example: recur/e CS1231 project meeting /start 1/10/2019 to 15/11/2019 /from 1500 /to 1700");
        }
    }
}