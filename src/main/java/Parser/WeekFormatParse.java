package Parser;

import Commons.LookupTable;
import DukeExceptions.DukeInvalidDateTimeException;
import DukeExceptions.DukeInvalidFormatException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeekFormatParse {
    private static LookupTable lookupTable = LookupTable.getInstance();
    private static String[] dateOfTask;


    public static String acadWeekToString (String weekDate, String date) throws DukeInvalidDateTimeException {

        if (weekDate.equalsIgnoreCase("reading") || weekDate.equalsIgnoreCase("exam")
                || weekDate.equalsIgnoreCase("week") || weekDate.equalsIgnoreCase("recess")) {

            dateOfTask = date.trim().split(" ");
            if (dateOfTask.length == 3) {
                weekDate = lookupTable.getValue(date) + " ";
            } else {
                throw new DukeInvalidDateTimeException("Sorry, please enter the correct date format");
            }
        } else {
            weekDate = date;
        }
        return weekDate;
    }

}
