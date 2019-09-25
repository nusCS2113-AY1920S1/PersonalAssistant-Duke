package task;

import exception.DukeException;
import parser.Parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Task class.
 * Base class which determines what kind of data a Task should hold.
 *
 * @author Kane Quah
 * @version 1.0
 * @since 08/2019
 */
public class Task {
    String description;
    boolean isDone;
    //protected String dueDate;
    private Date dueDate = null;
    private ArrayList<Date> tentativeDates = new ArrayList<>();
    protected static DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HHmm");
    protected static DateFormat dateFormatter_event = new SimpleDateFormat("dd-MM-yyyy HHmm-HHmm");

    /**
     * Task initialization with string as input.
     *
     * @param description String containing description information
     */
    Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Empty Task.
     */
    Task() {
    }

    /**
     * Get the prerequisite of the task as defined by the user.
     * To be overwritten by the After, Recurring, Within and Duration class
     */
    public String getAfter() {
        return "";
    }

    /**
     * Attempts to parse date and input it as Date type.
     *
     * @param date String which is in Date format
     * @throws DukeException DukeException thrown when dateFormatter parsing fails
     */
    void readDate(String date) throws DukeException {
        try {
            String[] split = date.split(Parser.dateSeparator);
            if (split.length == 1) {
                this.dueDate = dateFormatter.parse(date);
            } else {
                this.readDate(split);
            }
        } catch (ParseException e) {
            throw new DukeException("Error! Please enter date in the format DD-MM-YYYY 2359.");
            //throw new DukeException("Please use DDD format for date");
        }
    }


    /**
     * Attempts to parse dates and input it as Array of Date.
     *
     * @param input String Array which shall be in Date format
     * @throws DukeException DukeException thrown when dateFormatter parsing fails
     */
    void readDate(String[] input) throws DukeException {
        try {
            if (input.length == 1) {
                readDate(input[0]);
            } else {
                for (int i = 0; i < input.length; i++) {
                    this.tentativeDates.add(dateFormatter.parse(input[i]));
                }
            }
        } catch (ParseException | DukeException e) {
            throw new DukeException("Error! Please enter date in the format DD-MM-YYYY 2359.");
            //throw new DukeException("Please use DDD format for date");
        }
    }

    /**
     * checks if tentative Dates exist.
     *
     * @return true is it does else false
     */
    public boolean tentativeExists() {
        return !this.tentativeDates.isEmpty();
    }

    /**
     * Clears ArrayList of Tentative Dates.
     */
    void clearTentative() {
        this.tentativeDates.clear();
    }

    /**
     * Changes the task's Date to newDate.
     *
     * @param newDate is the new dueDate of the Task
     */
    void changeDueDate(Date newDate) {
        this.dueDate = newDate;
    }

    /**
     * Returns status icon.
     *
     * @return String which is a status icon in unicode format
     */
    String getStatusIcon() {
        return (isDone ? "Y" : "N"); //return Y or N
    }

    /**
     * Marks Task as Done, if Task is already done, throw exception.
     *
     * @throws DukeException DukeException warns the use that the Task has already been done
     */
    void markDone() throws DukeException {
        if (this.isDone) {
            throw new DukeException("But good sir, this task is already done!");
        } else {
            this.isDone = true;
        }
    }

    /**
     * empty method for overriding of child.
     *
     * @param snoozeDetails describes snooze.
     * @throws DukeException throws when snooze Details are incorrect.
     */
    void snooze(String snoozeDetails) throws DukeException {
    }

    /**
     * Returns true/false type based on whether Task has been marked done.
     *
     * @return boolean describing if Task is Done
     */
    public boolean checkCompletion() {
        return this.isDone;
    }

    /**
     * Returns description.
     *
     * @return String description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns converted Date type into String.
     *
     * @return String Date as per Date format
     */
    public String getDueDate() {
        if (this.dueDate != null) {
            return dateFormatter.format(this.dueDate);
        } else if (!this.tentativeDates.isEmpty()) {
            StringBuilder dates = new StringBuilder();
            dates.append(dateFormatter.format(this.tentativeDates.get(0)));
            for (int i = 1; i < this.tentativeDates.size(); i++) {
                dates.append(" OR ").append(dateFormatter.format(this.tentativeDates.get(i)));
            }
            return dates.toString();
        } else {
            return "";
        }
    }

    public Date getTentativeDate(int index) {
        return tentativeDates.get(index);
    }

    /**
     * Out of Bounds checker.
     *
     * @param request int The index to be checked if it exists
     * @return boolean true if within range, false if not
     */
    boolean outsideTentative(int request) {
        return ((request < 0) || (request >= this.tentativeDates.size()));
    }


    /**
     * Returns Task in print friendly format.
     *
     * @return String which contains Task Type icon, status and Description and DueDate if any
     */
    public String toList() {
        return "[?][" + this.getStatusIcon() + "] " + this.getDescription() + "(on:" + getDueDate() + ")";
    }

    /**
     * Returns type of Task.
     *
     * @return String consisting of a single Letter (for now)
     */
    public String getType() {
        return "G";
    }

}
