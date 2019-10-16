package duke.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event extends Item {
    /**
     * "at" is the date-time allocated to the task to be completed by.
     */
    private Date at;

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
     * Constructor method for the Event class.
     *
     * @param info   This is the information about the task being added
     * @param status This determines if whether the Item
     *               added is completed or uncompleted
     * @param date   the time to finish the task by
     */
    public Event(final String info, final Boolean status, final String date) {
        super(info, status);
        super.setType("E");
        this.at = dateConvert(date);
    }

    /**
     * This function takes the "at" data in the Event class.
     * Converts it into the string output format.
     * Format: 2nd of December 2019, 2pm.
     *
     * @return New string format
     */
    @Override
    public String getDate () {
        return dateToStringFormat(at);
    }

    /**
     * Function gets the unformatted date of at.
     *
     * @return at
     */
    @Override
    public Date getRawDate() {
        return this.at;
    }

    /**
     * This function gets the type, information, and date of the task.
     *
     * @return String phrase with the type, info and date
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + getDate() + ")";
    }
}
