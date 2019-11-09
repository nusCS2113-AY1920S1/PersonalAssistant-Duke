package Parser;

import Commons.DukeConstants;
import Commons.LookupTable;
import DukeExceptions.DukeInvalidDateTimeException;
import DukeExceptions.DukeInvalidFormatException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeekFormatParse {
    private static LookupTable lookupTable = LookupTable.getInstance();
    private static String[] dateOfTask;
    private static final Integer WEEK_ARRAY_LENGTH = 3;


    public static String acadWeekToString (String weekDate, String date) throws DukeInvalidDateTimeException {

        if (weekDate.equalsIgnoreCase("reading") || weekDate.equalsIgnoreCase("exam")
                || weekDate.equalsIgnoreCase("week") || weekDate.equalsIgnoreCase("recess")) {

            dateOfTask = date.trim().split(DukeConstants.STRING_SPACE_SPLIT_KEYWORD);
            if (dateOfTask.length == WEEK_ARRAY_LENGTH) {
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
