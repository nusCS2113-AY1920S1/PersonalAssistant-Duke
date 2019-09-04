import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Deadline extends Task {

    //deadline do homework /by no idea :-p
    //[D][✗] do homework (by: no idea :-p)
    //Deadline reads in an input, separate via a /by
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    //Format of deadline dd-MM-yyyy HHmm
    //Method to display deadline nicely when list is called
    //The deadline in save.txt remains in the format above
    public String getDeadline() {
        String[] month = { "January", "February", "March", "April",
                "May", "June", "July", "August",
                "September", "October", "November", "December" };

        String[] amPm = { "AM", "PM" };
        String niceDate = "";
        int hour;
        try {
            //This provides the pattern of the date input
            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HHmm");
            //This reads the date input in the format given
            Date newDate = dateFormatter.parse(this.by);
            //Creates a gregorian calender
            GregorianCalendar gcal = new GregorianCalendar();
            //Converts the date to a gregorian calender for easy usage
            gcal.setTime(newDate);

            //Calender HOUR method will return 0 if its 12, have to manually change
            if (gcal.get(Calendar.HOUR) == 0) {
                hour = 12;
            }
            else {
                hour = gcal.get(Calendar.HOUR);
            }

            niceDate = String.format("%d %s %d, %02d:%02d %s",
                    gcal.get(Calendar.DATE),
                    month[gcal.get(Calendar.MONTH)],
                    gcal.get(Calendar.YEAR),
                    hour,
                    gcal.get(Calendar.MINUTE),
                    amPm[gcal.get(Calendar.AM_PM)]);
        }

        catch (ParseException e) {
            System.out.println("Error. Please enter date in the format DD-MM-YYYY 2359.");
        }
        return niceDate;
    }

    public String getType() {
        return "[D]";
    }

    public String getBy() {
        return this.by;
    }

    public String getStatusIcon() {
        return "[D]" + "[" + (isDone ? "Y" : "N") + "] " + this.description + " (by: " + this.getDeadline() + ")";
    }
}
