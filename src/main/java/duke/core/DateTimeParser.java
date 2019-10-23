package duke.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class DateTimeParser {

    /**
     * update the <code> LocalDateTime </code> constructor to save the date and time.
     *
     * @param timeBeforeFormat the time retrieved from user input.
     * @return A LocalDateTime object that contains date and time information.
     */

    public static LocalDateTime convertToLocalDateTime(String timeBeforeFormat) throws DukeException {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(timeBeforeFormat, parser);
            return localDateTime;
        } catch (DateTimeParseException error) {
            throw new DukeException("Invalid format. Please Enter Date and Time in the format of dd/MM/yyyy HHmm");
        }
    }

    //@@author qjie7
    /**
     * Returns a string that representing the data and time for the task
     * in a predefined English date time format.
     *
     * @param timeBeforeFormat A String that provides the data and time information in dd/MM/yyyy HHmm.
     * @return A String that provides date and time in English
     */
    public static String convertToEnglishDateTime(String timeBeforeFormat) throws DukeException {
        DateTimeFormatter stFormatter = DateTimeFormatter.ofPattern("d'st of' MMMM yyyy, ha");
        DateTimeFormatter ndFormatter = DateTimeFormatter.ofPattern("d'nd of' MMMM yyyy, ha");
        DateTimeFormatter rdFormatter = DateTimeFormatter.ofPattern("d'rd of' MMMM yyyy, ha");
        DateTimeFormatter thFormatter = DateTimeFormatter.ofPattern("d'th of' MMMM yyyy, ha");

        try {
            LocalDateTime localDateTime;
            localDateTime = convertToLocalDateTime(timeBeforeFormat);
            if ((localDateTime.getDayOfMonth() % 10) == 1) {
                return localDateTime.format(stFormatter);
            } else if ((localDateTime.getDayOfMonth() % 10) == 2) {
                return localDateTime.format(ndFormatter);
            } else if ((localDateTime.getDayOfMonth() % 10) == 3) {
                return localDateTime.format(rdFormatter);
            } else {
                return localDateTime.format(thFormatter);
            }
        } catch (DukeException e) {
            throw e;
        }
    }

    /**
     *  .
     * @param localDateTime .
     * @return .
     */
    public static String convertToEnglishDateTimeBeforeParse(LocalDateTime localDateTime) {
        DateTimeFormatter stFormatter = DateTimeFormatter.ofPattern("d'st of' MMMM yyyy, ha");
        DateTimeFormatter ndFormatter = DateTimeFormatter.ofPattern("d'nd of' MMMM yyyy, ha");
        DateTimeFormatter rdFormatter = DateTimeFormatter.ofPattern("d'rd of' MMMM yyyy, ha");
        DateTimeFormatter thFormatter = DateTimeFormatter.ofPattern("d'th of' MMMM yyyy, ha");

        if ((localDateTime.getDayOfMonth() % 10) == 1) {
            return localDateTime.format(stFormatter);
        } else if ((localDateTime.getDayOfMonth() % 10) == 2) {
            return localDateTime.format(ndFormatter);
        } else if ((localDateTime.getDayOfMonth() % 10) == 3) {
            return localDateTime.format(rdFormatter);
        } else {
            return localDateTime.format(thFormatter);
        }
    }
    //@@author
}
