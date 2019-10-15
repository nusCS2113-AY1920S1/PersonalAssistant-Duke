package duke.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a recursive task that stores the same description and across the different dates.
 */
public class Repeat extends Task {
    protected Date from;
    protected String[] suf = { "st", "nd", "rd", "th" };
    protected SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");

    /**
     * Creates a repeated task with the description of task and date/time.
     *
     * @param description The description of the task.
     * @param from The date/time of the task.
     * @throws ParseException If there is an error converting the date/time.
     */
    public Repeat(String description, String from) throws ParseException {
        super(description);
        Date dateTime;
        try {
            dateTime = datetimeFormat.parse(from);
            this.from = dateTime;
        } catch (ParseException e) {
            System.out.println("Error reading date/time, please use this format \"d/MM/yyyy HHmm\"");
            throw e;
        }
    }

    /**
     * Extracting a task content into readable string.
     *
     * @return String to be displayed.
     */
    @Override
    public String toString() {
        SimpleDateFormat datetimeFormat2 = new SimpleDateFormat("MMMMM yyyy, h:mm a");
        SimpleDateFormat datetimeFormat3 = new SimpleDateFormat("MMMMM yyyy, ha");
        String displayDT = "";

        int day = Integer.parseInt(new SimpleDateFormat("d").format(from));
        int min = Integer.parseInt(new SimpleDateFormat("m").format(from));
        if (min > 0) {
            displayDT = datetimeFormat2.format(from);
        } else {
            displayDT = datetimeFormat3.format(from);
        }
        int sufIndex = -1;

        if (day == 1 || day == 21 || day == 31) {
            sufIndex = 0;
        } else if (day == 2 || day == 22) {
            sufIndex = 1;
        } else if (day == 3 || day == 23) {
            sufIndex = 2;
        } else if (day > 3 && day < 31) {
            sufIndex = 3;
        }
        String suffixStr = day + suf[sufIndex];
        displayDT = suffixStr + " of " + displayDT;
        return "[R]" + super.toString() + " (Last day of schedule: " + displayDT + ")";
    }

    /**
     * Extracting a task content into readable string (GUI).
     *
     * @return String to be displayed.
     */
    @Override
    public String toStringGui() {
        SimpleDateFormat datetimeFormat2 = new SimpleDateFormat("MMMMM yyyy, h:mm a");
        SimpleDateFormat datetimeFormat3 = new SimpleDateFormat("MMMMM yyyy, ha");
        String displayDT = "";

        int day = Integer.parseInt(new SimpleDateFormat("d").format(from));
        int min = Integer.parseInt(new SimpleDateFormat("m").format(from));
        if (min > 0) {
            displayDT = datetimeFormat2.format(from);
        } else {
            displayDT = datetimeFormat3.format(from);
        }
        int sufIndex = -1;

        if (day == 1 || day == 21 || day == 31) {
            sufIndex = 0;
        } else if (day == 2 || day == 22) {
            sufIndex = 1;
        } else if (day == 3 || day == 23) {
            sufIndex = 2;
        } else if (day > 3 && day < 31) {
            sufIndex = 3;
        }
        String suffixStr = day + suf[sufIndex];
        displayDT = suffixStr + " of " + displayDT;
        return "[R]" + super.toStringGui() + " (Last day of schedule: " + displayDT + ")";
    }

    /**
     * Retrieves the date of the task as a String format.
     *
     * @return String of Date.
     */
    @Override
    public String getDateTime() {
        String datetimeStr = datetimeFormat.format(from);
        return datetimeStr;
    }

    /**
     * Sets the date of the task.
     */
    @Override
    public void setDateTime(String from) throws Exception {
        Date dateTime;
        try {
            dateTime = datetimeFormat.parse(from);
            this.from = dateTime;
        } catch (ParseException e) {
            System.out.println("Error reading date/time, please use this format \"d/MM/yyyy HHmm\"");
            throw e;
        }
    }

    /**
     * Extracting a task content into string that is suitable for text file.
     *
     * @return String to be written into text file.
     */
    @Override
    public String toFile() {
        String datetimeStr = datetimeFormat.format(from);
        return "R|" + super.toFile() + "|" + datetimeStr;
    }
}
