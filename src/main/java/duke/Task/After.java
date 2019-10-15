package duke.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class After extends Item {
    /**
     * "after" is the date allocated to the task to be completed by.
     */
    private Date after;

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

    public After(String info, Boolean status, String after) {
        super(info, status);
        super.setType("A");
        this.after = dateConvert(after);
    }

    /**
     * This function takes the "after" data in the After class.
     * Converts it into the string output format.
     * Format: 2nd of December 2019, 2pm.
     *
     * @return New string format
     */
    @Override
    public String getDate () {
        return dateToStringFormat(after);
    }

    /**
     * Function gets the unformatted date of after.
     *
     * @return after
     */
    @Override
    public Date getRawDate() {
        return this.after;
    }

    /**
     * Overrides the toString function in Item.
     * Extends function to include After class info.
     *
     * @return string of data with After class info
     */
    @Override
    public String toString() {
        return "[A]" + super.toString() + " (after: " + getDate() + ")";
    }
}
