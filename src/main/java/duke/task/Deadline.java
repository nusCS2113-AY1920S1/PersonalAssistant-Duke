package duke.task;

import duke.dukeexception.DukeException;
import duke.enums.Numbers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author talesrune
/**
 * Represents a deadline that stores description and date/time.
 */
public class Deadline extends Task {
    protected Date by;
    protected String[] suf = { "st", "nd", "rd", "th" };
    protected SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
    private static final Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Creates a deadline with the specified description and date/time.
     *
     * @param description The description of the task.
     * @param by The date/time of the task.
     * @throws DukeException  If there is an error converting the date/time.
     */
    public Deadline(String description, String by) throws DukeException {
        super(description);
        Date dateTime;
        try {
            dateTime = datetimeFormat.parse(by);
            this.by = dateTime;
        } catch (ParseException e) {
            logr.log(Level.WARNING,"Error reading date/time, please use this format \"d/MM/yyyy HHmm\"", e);
            System.out.println("Error reading date/time, please use this format \"d/MM/yyyy HHmm\"");
            throw new DukeException("Error reading date/time, please use this format \"d/MM/yyyy HHmm\"");
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
        if (min > Numbers.ZERO.value) {
            displayDT = datetimeFormat2.format(by);
        } else {
            displayDT = datetimeFormat3.format(by);
        }
        int sufIndex = Numbers.MINUS_ONE.value;

        if (day == Numbers.ONE.value || day == Numbers.TWENTY_ONE.value || day == Numbers.THIRTY_ONE.value) {
            sufIndex = Numbers.ZERO.value;
        } else if (day == Numbers.TWO.value || day == Numbers.TWENTY_TWO.value) {
            sufIndex = Numbers.ONE.value;
        } else if (day == Numbers.THREE.value || day == Numbers.TWENTY_THREE.value) {
            sufIndex = Numbers.TWO.value;
        } else if (day > Numbers.THREE.value && day < Numbers.THIRTY_ONE.value) {
            sufIndex = Numbers.THREE.value;
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
        if (min > Numbers.ZERO.value) {
            displayDT = datetimeFormat2.format(by);
        } else {
            displayDT = datetimeFormat3.format(by);
        }
        int sufIndex = Numbers.MINUS_ONE.value;

        if (day == Numbers.ONE.value || day == Numbers.TWENTY_ONE.value || day == Numbers.THIRTY_ONE.value) {
            sufIndex = Numbers.ZERO.value;
        } else if (day == Numbers.TWO.value || day == Numbers.TWENTY_TWO.value) {
            sufIndex = Numbers.ONE.value;
        } else if (day == Numbers.THREE.value || day == Numbers.TWENTY_THREE.value) {
            sufIndex = Numbers.TWO.value;
        } else if (day > Numbers.THREE.value && day < Numbers.THIRTY_ONE.value) {
            sufIndex = Numbers.THREE.value;
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
            logr.log(Level.WARNING,"Error reading date/time, please use this format \"d/MM/yyyy HHmm\"", e);
            System.out.println("Error reading date/time, please use this format \"d/MM/yyyy HHmm\"");
            throw e;
        }
    }
}