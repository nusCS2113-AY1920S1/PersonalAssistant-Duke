package duke.parser;

import duke.exception.DukeException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Convert between String and Date.
 */
public class Convert {

    //@@author CEGLincoln
    /**
     * Returns the suffix to be used after the days in the Date, useful for printing the Date in the desired format.
     *
     * @param n indication the Day of the month
     * @return the suffix accordingly to the day of the month needed
     */
    public static String getDaySuffix(int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    //@@author CEGLincoln
    /**
     * Returns a {@link Date} representation of a String in the format "dd/MM/yyyy hhmm" or "dd/MM/yyyy".
     *
     * @param date String in the format "dd/MM/yyyy hhmm" or "dd/MM/yyyy", used to extract a {@link Date} instance from
     * @return The {@link Date} instance created from the argument string or null
     */
    public static Date stringToDate(String date) throws DukeException {
        DateFormat formatter;
        if (date.length() > 11) {
            formatter = new SimpleDateFormat("dd/MM/yyyy hhmm");
        } else {
            formatter = new SimpleDateFormat("dd/MM/yyyy");
        }
        checkValidDate(date);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            System.out.println("\t Warning: Unable to convert \"" + date + "\" to a Date.");
            return null;
        }
    }


    //@@author CEGLincoln
    /**
     * Returns the {@link Date } instance as a String to be printed in the file.
     *
     * @param date deadline {@link Date} for finishing the task
     * @return String the date for the deadline
     */
    public static String getDateString(Date date, String dateString) {
        if (date == null) {
            return dateString;
        }
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String pattern;
        if (dateString.length() > 11) {
            pattern = "d'" + getDaySuffix(localDate.getDayOfMonth()) + "' 'of' MMMM yyyy, ha ";
        } else {
            pattern = "d'" + getDaySuffix(localDate.getDayOfMonth()) + "' 'of' MMMM yyyy";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    /**
     * @author VirginiaYu
     *
     * Check if the input date string is in valid format dd/mm/yyyy and
     * check if the date is a real date
     *
     * @param date input date string
     * @throws DukeException exception of invalid date
     */
    public static void checkValidDate(String date) throws DukeException {
        boolean flag = true;
        String[] dateMonthYear = date.split("\\/",3);
        if (dateMonthYear.length!=3) {
            throw new DukeException("Invalid date");
        } else {
            try{
                int dd = Integer.parseInt(dateMonthYear[0]);
                int mm = Integer.parseInt(dateMonthYear[1]);
                int yy = Integer.parseInt(dateMonthYear[2]);
                if (dd<1||dd>31||mm<1||mm>12||yy<1000||yy>9999) throw new DukeException("Must enter a valid date");
                if (mm==2&&(dd==31||dd==30||dd==29)) throw new DukeException("Must enter a valid date");
                if (dd==31&&(mm==4||mm==6||mm==9||mm==11)) throw new DukeException("Must enter a valid date");
            } catch (NumberFormatException e) {
                e.getStackTrace();
            }
        }
    }
}
