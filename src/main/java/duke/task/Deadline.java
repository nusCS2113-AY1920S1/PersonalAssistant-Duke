package duke.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

//@@author talesrune
/**
 * Represents a deadline that stores description and date/time.
 */
public class Deadline extends Task {
    protected Date by;
    protected String[] suf = { "st", "nd", "rd", "th" };
    protected SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int MINUS_ONE = -1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int TWENTY_ONE = 21;
    private static final int TWENTY_TWO = 22;
    private static final int TWENTY_THREE = 23;
    private static final int THIRTY_ONE = 31;

    /**
     * Creates a deadline with the specified description and date/time.
     *
     * @param description The description of the task.
     * @param by The date/time of the task.
     * @throws ParseException  If there is an error converting the date/time.
     */
    public Deadline(String description, String by) throws ParseException {
        super(description);
        Date dateTime;
        try {
            dateTime = datetimeFormat.parse(by);
            this.by = dateTime;
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

        int day = Integer.parseInt(new SimpleDateFormat("d").format(by));
        int min = Integer.parseInt(new SimpleDateFormat("m").format(by));
        if (min > ZERO) {
            displayDT = datetimeFormat2.format(by);
        } else {
            displayDT = datetimeFormat3.format(by);
        }
        int sufIndex = MINUS_ONE;

        if (day == ONE || day == TWENTY_ONE || day == THIRTY_ONE) {
            sufIndex = ZERO;
        } else if (day == TWO || day == TWENTY_TWO) {
            sufIndex = ONE;
        } else if (day == THREE || day == TWENTY_THREE) {
            sufIndex = TWO;
        } else if (day > THREE && day < THIRTY_ONE) {
            sufIndex = THREE;
        }
        String suffixStr = day + suf[sufIndex];
        displayDT = suffixStr + " of " + displayDT;
        return "[D]" + super.toString() + " (by: " + displayDT + ")";
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

        int day = Integer.parseInt(new SimpleDateFormat("d").format(by));
        int min = Integer.parseInt(new SimpleDateFormat("m").format(by));
        if (min > ZERO) {
            displayDT = datetimeFormat2.format(by);
        } else {
            displayDT = datetimeFormat3.format(by);
        }
        int sufIndex = MINUS_ONE;

        if (day == ONE || day == TWENTY_ONE || day == THIRTY_ONE) {
            sufIndex = ZERO;
        } else if (day == TWO || day == TWENTY_TWO) {
            sufIndex = ONE;
        } else if (day == THREE || day == TWENTY_THREE) {
            sufIndex = TWO;
        } else if (day > THREE && day < THIRTY_ONE) {
            sufIndex = THREE;
        }
        String suffixStr = day + suf[sufIndex];
        displayDT = suffixStr + " of " + displayDT;
        return "[D]" + super.toStringGui() + " (by: " + displayDT + ")";
    }

    /**
     * Extracting a task content into string that is suitable for text file.
     *
     * @return String to be written into text file.
     */
    @Override
    public String toFile() {
        String datetimeStr = datetimeFormat.format(by);
        return "D|" + super.toFile() + "|" + datetimeStr;
    }

    /**
     * Retrieves the date of the task as a String format.
     *
     * @return String of Date.
     */
    @Override
    public String getDateTime() {
        String datetimeStr = datetimeFormat.format(by);
        return datetimeStr;
    }

    /**
     * Sets the date of the task.
     */
    @Override
    public void setDateTime(String by) throws Exception {
        Date dateTime;
        try {
            dateTime = datetimeFormat.parse(by);
            this.by = dateTime;
        } catch (ParseException e) {
            System.out.println("Error reading date/time, please use this format \"d/MM/yyyy HHmm\"");
            throw e;
        }
    }
}