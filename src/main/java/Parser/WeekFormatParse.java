package Parser;

import Commons.DukeConstants;
import Commons.LookupTable;
import DukeExceptions.DukeInvalidDateTimeException;

/**
 * This class parses the full command that calls for WeekFormatParse.
 */
public class WeekFormatParse {
    private static LookupTable lookupTable = LookupTable.getInstance();
    private static String[] dateOfTask;
    private static final Integer WEEK_ARRAY_LENGTH = 3;

    /**
     * This method process the input date and output into the relevant format.
     * @param weekDate The date format or week format
     * @param date The date given
     * @return The output format in date
     * @throws DukeInvalidDateTimeException The error where the date given is invalid
     */
    public static String acadWeekToString(String weekDate, String date) throws DukeInvalidDateTimeException {

        if (weekDate.equalsIgnoreCase(DukeConstants.WEEK_FORMAT_KEYWORD_READING)
                || weekDate.equalsIgnoreCase(DukeConstants.WEEK_FORMAT_KEYWORD_EXAM)
                || weekDate.equalsIgnoreCase(DukeConstants.WEEK_FORMAT_KEYWORD)
                || weekDate.equalsIgnoreCase(DukeConstants.WEEK_FORMAT_KEYWORD_RECESS)) {

            dateOfTask = date.trim().split(DukeConstants.BLANK_SPACE);
            if (dateOfTask.length == WEEK_ARRAY_LENGTH) {
                weekDate = lookupTable.getValue(date) + DukeConstants.BLANK_SPACE;
            } else {
                throw new DukeInvalidDateTimeException(DukeConstants.INVALID_DATE_ERROR);
            }
        } else {
            String nullChecker = lookupTable.getValue(weekDate);
            if (nullChecker == null) {
                throw new DukeInvalidDateTimeException(DukeConstants.INVALID_ACADEMIC_YEAR_DATE);
            }
            weekDate = date;
        }
        return weekDate;
    }
}
