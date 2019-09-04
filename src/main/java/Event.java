import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Event extends Task {

    protected String at;

    public Event(String description, String at) {
        super(description);
        this.at = at;
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
            Date newDate = dateFormatter.parse(this.at);
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
        return "[E]";
    }

    public String getBy() {
        return this.at;
    }

    public String getStatusIcon() {
        return "[E]" + "[" + (isDone ? "Y" : "N") + "] " + this.description + " (at: " + this.getDeadline() + ")";
    }
}
