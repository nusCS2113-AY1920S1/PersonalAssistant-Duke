package parser;

import commands.Command;
import commons.DukeConstants;
import java.text.ParseException;
import java.util.Date;

/**
 * Abstract class Parse with methods representing all the Command subclasses to be
 * carried out when an input is entered by the user.
 */
public abstract class Parse {
    private static final int LOWER_BOUND_OF_TIME = 0;
    private static final int UPPER_BOUND_OF_TIME = 2359;
    private static final int UPPER_BOUND_OF_HOUR = 23;
    private static final int UPPER_BOUND_OF_MINUTE = 59;
    private static final int TIME_SEPARATOR = 100;
    private static final int WRONG_LENGTH_OF_MODCODE_DESCRIPTION = 1;

    public abstract Command parse() throws Exception;

    /**
     * This method checks if the user input start and end time actually fits the characteristics of a
     * 24-hour time format.
     * @param input The string that contains the  date, start and end time fields
     * @return true if it matches the characteristics of a 24-hour time format
     */
    public boolean isValidTimePeriod(String input) {
        String[] dateTimeStringSplit = input.trim().split(DukeConstants.EVENT_DATE_SPLIT_KEYWORD);
        String[] timeStringSplit = dateTimeStringSplit[1].split(DukeConstants.EVENT_TIME_SPLIT_KEYWORD);
        String start = timeStringSplit[0].trim();
        String end = timeStringSplit[1].trim();
        if (start.length() != DukeConstants.LENGTH_OF_TIME_FORMAT
                || end.length() != DukeConstants.LENGTH_OF_TIME_FORMAT) {
            return false;
        } else if ((start.matches("[0-9]+")) || (end.matches("[0-9]"))) {
            Integer intStart = Integer.parseInt(start);
            Integer intEnd = Integer.parseInt(end);
            if (!isValidTwentyFourHourFormat(intStart) || !isValidTwentyFourHourFormat(intEnd)) {
                return false;
            } else if (intStart > intEnd) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * This method checks if the time given is in a 24-hour time format.
     * @param intTime The time given
     * @return true if the time given is valid. Otherwise, false.
     */
    public boolean isValidTwentyFourHourFormat(Integer intTime) {
        if (intTime < LOWER_BOUND_OF_TIME || intTime > UPPER_BOUND_OF_TIME) {
            return false;
        } else {
            Integer intHour = intTime / TIME_SEPARATOR;
            Integer intMinute = intTime % TIME_SEPARATOR;
            if (intHour > UPPER_BOUND_OF_HOUR  || intMinute > UPPER_BOUND_OF_MINUTE) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * This method checks if the user input time actually fits the characteristics of a 24-hour time format.
     * @param input The string that contains the date and time field
     * @return true if it matches the characteristics of a 24-hour time format
     */
    public boolean isValidTime(String input) {
        String[] spiltInput = input.split(DukeConstants.BLANK_SPACE);
        String time = spiltInput[spiltInput.length - 1];
        if (time.length() != DukeConstants.LENGTH_OF_TIME_FORMAT) {
            return false;
        }
        if (time.matches("[0-9]+")) {
            Integer intTime = Integer.parseInt(time);
            return isValidTwentyFourHourFormat(intTime);
        }
        return false;
    }

    /**
     * This method checks if the description of task if valid.
     * @param input String array of ModCode and description
     * @return true if description of task is valid
     */
    public boolean isValidDescription(String[] input) {
        if (input.length == WRONG_LENGTH_OF_MODCODE_DESCRIPTION) {
            return false;
        }
        String description = input[1].trim();
        if (description.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * This method checks if both the ModCode and description of the task is valid.
     * @param input String of ModCode and description
     * @return true if ModCode and description of the task is valid
     */
    public boolean isValidModCodeAndDescription(String input) {
        if (input.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * This method checks if the time for the start date and end date is valid.
     * @param start The start date
     * @param end The end date
     * @return true if the start date time and end date time is valid
     * @throws ParseException on the wrong format of date
     */
    public boolean isValidDateRecurring(String start, String end) throws ParseException {
        Date startDate = DukeConstants.DAY_DATE_FORMAT.parse(start);
        Date endDate = DukeConstants.DAY_DATE_FORMAT.parse(end);
        if (startDate.getTime() > endDate.getTime()) {
            return false;
        }
        return true;
    }
}
