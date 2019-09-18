package Events.Formatting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Model_Class.DateObj object stores the input date and time as a java object.
 */

public class DateObj {

    /**
     * The javaDate variable stores the date and time as a java Date object
     * if the input is in the format dd/mm/yyyy HHmm (24-hr clock) or
     * dd/mm/yyyy.
     */
    protected Date javaDate;

    /**
     * The java date variable stores the date and time as a string if the input is
     * not in either of the two formats defined for the dateobj variable.
     */
    protected String date;

    /**
     * Stores the format type of the date input.
     */
    protected int format;

    protected static int DATE_AND_TIME = 1;
    protected static int DATE = 2;
    protected static int OTHER = 3;

    /**
     * Creates a custom "date object".
     * It processes the input according to the description stated above.
     *
     * @param inputDate the input keyed in for the date.
     */
    public DateObj(String inputDate) {
        try {
            SimpleDateFormat inputFormat1 = new SimpleDateFormat("dd/MM/yyyy HHmm");
            SimpleDateFormat inputFormat2 = new SimpleDateFormat("dd MMMMM yyyy, KK:mm a");
            inputFormat1.setLenient(false);
            inputFormat2.setLenient(false);
            Date newJavaDate;
            if (inputDate.contains("/")) { //normal date input type from user
                newJavaDate = inputFormat1.parse(inputDate);
            } else { //date type from tasks by getDate()
                newJavaDate = inputFormat2.parse(inputDate);
            }
            this.javaDate = newJavaDate;
            format = DATE_AND_TIME; // date and time
        } catch (ParseException pe) {
            try {
                SimpleDateFormat inputFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat inputFormat2 = new SimpleDateFormat("dd MMMMM yyyy");
                inputFormat1.setLenient(false);
                inputFormat2.setLenient(false);
                Date newJavaDate;
                if (inputDate.contains("/")) { //normal date input type from user
                    newJavaDate = inputFormat1.parse(inputDate);
                } else { //date type from tasks by getDate()
                    newJavaDate = inputFormat2.parse(inputDate);
                }
                this.javaDate = newJavaDate;
                format = DATE; // only date
            } catch (ParseException pe2) {
                format = OTHER; // other types; store as string
                this.date = inputDate;
            }
        }
    }

    /**
     * Converts deadline type task to string format for printing.
     *
     * @return Formatted string representing the deadline and its date.
     */
    public String toOutputString() {
        if (format == DATE_AND_TIME) {
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm aa");
            return outputFormat.format(javaDate);
        } else if (format == DATE) {
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
            String out = outputFormat.format(javaDate);
            return outputFormat.format(javaDate);
        } else {
            return date;
        }
    }

    public Date getJavaDate() {
        return javaDate;
    }

    public int getFormat() {
        return format;
    }
}
