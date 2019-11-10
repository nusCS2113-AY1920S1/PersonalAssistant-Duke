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

        if (weekDate.equalsIgnoreCase(DukeConstants.WEEK_FORMAT_KEYWORD_READING) || weekDate.equalsIgnoreCase(DukeConstants.WEEK_FORMAT_KEYWORD_EXAM)
                || weekDate.equalsIgnoreCase(DukeConstants.WEEK_FORMAT_KEYWORD) || weekDate.equalsIgnoreCase(DukeConstants.WEEK_FORMAT_KEYWORD_RECESS)) {

            dateOfTask = date.trim().split(DukeConstants.BLANK_SPACE);
            if (dateOfTask.length == WEEK_ARRAY_LENGTH) {
                weekDate = lookupTable.getValue(date) + DukeConstants.BLANK_SPACE;
            } else {
                throw new DukeInvalidDateTimeException(DukeConstants.INVALID_DATE_ERROR);
            }
        } else {
            weekDate = date;
        }
        return weekDate;
    }
}
