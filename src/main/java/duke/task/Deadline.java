package duke.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Creates a class for the Deadline task.
 */
public class Deadline extends Item {
    /**
     * "by" is the date-time allocated to the task to be completed by.
     */
    private Date by;

    public Date dateConvert (String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
            Date formatDate = simpleDateFormat.parse(date);
            return formatDate;
        }
        catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Please enter a valid date format");
            return null;
        }
        catch (ParseException pe) {
            System.out.println("Date error");
            return null;
        }
    }

    public String dateToStringFormat (Date date) {
        String hour =  new SimpleDateFormat("h").format(date);
        String min = new SimpleDateFormat("mm").format(date);
        String marker = new SimpleDateFormat("a").format(date);
        String day = new SimpleDateFormat("d").format(date);
        String monthYear = new SimpleDateFormat("MMMMM yyyy").format(date);
        String newDateFormat = this.numOrdinal(Integer.parseInt(day)) + " of " + monthYear + ", " +
                hour + (min.equals("00") ? marker : ("." + min + marker));
        return newDateFormat;
    }

    public String numOrdinal (int num) {
        String[] suffix = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (num) {
            case 11:
            case 12:
            case 13:
                return num + "th";
            default:
                return num + suffix[num % 10];
        }
    }

    /**
     * Constructor method for the Deadline class.
     *
     * @param info   This parameter is the info of the Item created
     * @param status The status of the Item created, either true or false
     * @param date     the time to finish the task by
     */
    public Deadline(final String info,
                    final Boolean status,
                    final String date) {
        super(info, status);
        super.setType("D");
        this.by = dateConvert(date);
    }

    /**
     * This function takes the "by" data in the Event class.
     * Converts it into the string output format.
     * Format: 2nd of December 2019, 2pm.
     *
     * @return New string format
     */
    @Override
    public String getDate () {
        return dateToStringFormat(by);
    }


    /**
     * Function gets the unformatted date of by.
     *
     * @return by
     */
    @Override
    public Date getRawDate() {
        return this.by;
    }

    /**
     * Gets all the info of the deadline.
     *
     * @return A string phrase containing all the details of the deadline object
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + getDate() + ")";
    }

}
