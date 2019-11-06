package duke.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

//@@author talesrune-unused
/**
 * Code for event task will not be used as the final product as it is very similar to Deadline.
 *
 * Represents an event that stores description and date/time.
 */

public class Event extends Task {
    protected Date at;
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
     * Creates an event with the specified description and date/time.
     *
     * @param description The description of the task.
     * @param at The date/time of the task.
     * @throws ParseException  If there is an error converting the date/time.
     */
    public Event(String description, String at) throws ParseException {
        super(description);

        Date dateTime = null;
        try {
            dateTime = datetimeFormat.parse(at);
            this.at = dateTime;
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

        int day = Integer.parseInt(new SimpleDateFormat("d").format(at));
        int min = Integer.parseInt(new SimpleDateFormat("m").format(at));
        if (min > ZERO) {
            displayDT = datetimeFormat2.format(at);
        } else {
            displayDT = datetimeFormat3.format(at);
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
        return "[E]" + super.toString() + " (at: " + displayDT + ")";
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

        int day = Integer.parseInt(new SimpleDateFormat("d").format(at));
        int min = Integer.parseInt(new SimpleDateFormat("m").format(at));
        if (min > ZERO) {
            displayDT = datetimeFormat2.format(at);
        } else {
            displayDT = datetimeFormat3.format(at);
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
        return "[E]" + super.toStringGui() + " (at: " + displayDT + ")";
    }

    /**
     * Extracting a task content into string that is suitable for text file.
     *
     * @return String to be written into text file.
     */
    @Override
    public String toFile() {
        String datetimeStr = datetimeFormat.format(at);
        return "E|" + super.toFile() + "|" + datetimeStr;
    }

    /**
     * Retrieves the date of the task as a String format.
     *
     * @return String of Date.
     */
    @Override
    public String getDateTime() {
        String datetimeStr = datetimeFormat.format(at);
        return datetimeStr;
    }

    /**
     * Sets the date of the task.
     */
    @Override
    public void setDateTime(String at) throws Exception {
        Date dateTime;
        try {
            dateTime = datetimeFormat.parse(at);
            this.at = dateTime;
        } catch (ParseException e) {
            System.out.println("Error reading date/time, please use this format \"d/MM/yyyy HHmm\"");
            throw e;
        }
    }
}

/**
 * Code snippet from executeGui function in UpdateCommand Class
 */
/*
               else if (typeDesc.equals("event")) {
                   if (items.get(index) instanceof Deadline || items.get(index) instanceof Repeat) {
                       newtaskObj = new Event(items.get(index).getDescription(), items.get(index).getDateTime());
                   } else if (items.get(index) instanceof Event) {
                       //throw new DukeException("     You are updating the same type of task! (Event)");
                       return "     (>_<) OOPS!!! You are updating the same type of task! (Event)";
                   } else {
                       newtaskObj = new Event(items.get(index).getDescription(), "01/01/2001 0001");
                   }
               }
*/

/**
 * Code snippet from read function in Storage Class
 */
/*
               else if (commandList[ZERO].equals("E")) {
                   if (taskDesc.isEmpty() || dateDesc.isEmpty()) {
                       throw new DukeException("Error reading description or date/time, skipping to next line");
                   } else {
                       t = new Event(taskDesc, dateDesc);
                       t.setStatusIcon(checked);
                       t.setNotes(notesDesc);
                       items.add(t);
                   }
               }
*/