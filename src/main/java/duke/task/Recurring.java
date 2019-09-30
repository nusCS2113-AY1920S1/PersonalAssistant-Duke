package duke.task;

import duke.command.*;
import duke.core.*;
import duke.exception.*;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * Represents a Recurring task. Stores a description, date and whether it is done.
 */

 // when recurs and next recur time!

public class Recurring extends Task {
    /**
     * Constructor for a new Recurring task. Called when a Recurring task is first created.
     * @param description a String description of the Deadline.
     * @param by a String done-by date
     * @throws ParseException if date does not follow the required format
     */
    public Recurring(String description, String day, String time) throws DukeException, ParseException {
        super(description);
        this.day = day;
        this.time = time;
        this.type = 'R';
        findNextAvailableDate();
    }

    /**
     * Constructor for an existing Deadline from our saved tasks. Called to
     * create saved tasks.
     * @param description a String description of the Deadline.
     * @param by a String done-by date
     * @param isDone a Boolean indicating whether the task has been fulfilled.
     * @throws ParseException if date does not follow the required format
     */
    public Recurring(String description, String day, String time, boolean isDone) throws DukeException, ParseException {
        super(description, isDone);
        this.day = day;
        this.time = time;
        this.type = 'R';
        findNextAvailableDate();
    }

    public void findNextAvailableDate() throws DukeException, ParseException {
        Date todayDate = new Date(System.currentTimeMillis());
        boolean validDay = false;
        for (int i = 0; i < 7; i++) {
            if (new SimpleDateFormat("EEEEE").format(todayDate).equals(day) && !(i == 0 && (new SimpleDateFormat("HHmm").format(todayDate)).compareTo(time) > 0)) {
                validDay = true;
                this.date = todayDate;
                String todayStringDate = getDateAsString();
                todayStringDate = todayStringDate.substring(0, todayStringDate.length() - 4);
                todayStringDate += time;
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
                Date dateValue = formatter.parse(todayStringDate);
                this.date = dateValue;
            }
            else {
                Calendar cal = Calendar.getInstance();
                cal.setTime(todayDate);
                cal.add(Calendar.DATE, 1);
                todayDate = cal.getTime();
            }
        }
        if (!(validDay)) {
            throw new DukeException("Please enter a valid day / time.");
        }
    }

    /**
     * Converts the stored Date of the Deadline to a readable String for output to the CLI.
     * @return a String version of the stored Date.
     */
    @Override
    public String toString() {
        return super.toString() + " (next: " + getDateToPrint() + ")";
    }
}