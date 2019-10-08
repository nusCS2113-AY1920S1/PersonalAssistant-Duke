package compal.tasks;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Represents task with description, status and reminder.
 */
public abstract class Task implements Serializable {

    public enum Priority {
        high, medium, low;
    }

    //***Class Properties/Variables***--------------------------------------------------------------------------------->

    protected String symbol;
    private String description;
    public boolean isDone;

    private Date date;   //For now, we only process dates in the format dd/mm/yyyy hhmm. See TaskList class for details
    private Date time;
    private Integer durationHour = 0;
    private Integer durationMinute = 0;
    private boolean hasReminder;
    private Priority priority;
    private long priorityScore;
    //----------------------->


    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    /**
     * Constructs Task object.
     *
     * @param description Description.
     */
    public Task(String description, Priority priority) {
        this.description = description;
        this.priority = priority;
        this.isDone = false;
        hasReminder = false;

    }
    //----------------------->


    /**
     * Gets priority status (HIGH, MEDIUM, LOW) of task.
     *
     * @return Priority status of task.
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Gets status icon (tick or cross) of task.
     *
     * @return Status icon (tick or cross) of task.
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }

    /**
     * Gets status icon (tick or cross) of task.
     *
     * @return Status icon (tick or cross) of task.
     */
    public String getisDone() {
        return (isDone ? "true" : "false");
    }

    /**
     * Gets status icon (tick or cross) of task.
     *
     * @return Status icon (tick or cross) of task.
     */
    public String gethasReminder() {
        return (hasReminder ? "true" : "false");
    }

    /**
     * Gets symbol of task.
     *
     * @return Symbol of task.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets date of task in date format.
     *
     * @return Date of task.
     */
    public Date getDate() {
        /*Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        this.date = calendar.getTime();*/
        return this.date;
    }

    /**
     * Sets priority of task as HIGH, MEDIUM or LOW.
     *
     * @param priority Priority of task.
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Formats dateInput then sets date as dateInput.
     *
     * @param dateInput Input date of task.
     */
    public void setDate(String dateInput) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(dateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.date = date;
    }

    /**
     * Gets date of task in string.
     *
     * @return Date of task.
     */
    public String getStringDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = formatter.format(this.date);
        return stringDate;
    }

    /**
     * Gets durationHour of task.
     *
     * @return Hour duration of task.
     */
    public Integer getDurationHour() {
        return durationHour;
    }

    /**
     * Sets durationHour as input durationHour.
     *
     * @param durationHour Input duration hour.
     */
    public void setDurationHour(Integer durationHour) {
        this.durationHour = durationHour;
    }

    /**
     * Gets durationMinute of task.
     *
     * @return Minute duration of task.
     */
    public Integer getDurationMinute() {
        return durationMinute;
    }

    /**
     * Sets durationMinute as input durationMinute.
     *
     * @param durationMinute Input duration minute.
     */
    public void setDurationMinute(Integer durationMinute) {
        this.durationMinute = durationMinute;
    }

    /**
     * Gets hasReminder of task.
     *
     * @return whether the task has reminder. If task has reminder, return true. Else false.
     */
    public boolean hasReminder() {
        return hasReminder;
    }


    /**
     * Gets time of task in date format.
     *
     * @return Time of task.
     */
    public Date getTime() {
        return time;
    }

    /**
     * Formats timeInput then sets time as timeInput.
     *
     * @param timeInput Input time of task.
     */
    public void setTime(String timeInput) {
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        Date time = null;
        try {
            time = format.parse(timeInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.time = time;
    }

    /**
     * Gets time of task in string.
     *
     * @return Time of task.
     */
    public String getStringTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HHmm");
        if (this.time == null) {
            return "-";
        } else {
            return formatter.format(this.time);
        }
    }

    /**
     * Gets description of task.
     *
     * @return Description of task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets priorityScore.
     *
     * @return priority score
     */
    public long getPriorityScore() {
        return priorityScore;
    }

    /**
     * Sets isDone as true.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Sets HasReminder as true.
     */
    public void setHasReminder() {
        this.hasReminder = true;
    }
    //----------------------->

    /**
     * Returns the task as a formatted string.
     * This function standardizes the displayed task format.
     *
     * @return Task as a formatted string.
     */
    @Override
    public String toString() {
        if (getDurationHour() != null && getDurationMinute() != null) {
            return "[" + getSymbol() + "]" + "[" + getStatusIcon() + "] " + getDescription()
                    + " Date: " + getStringDate() + " Hour: " + getDurationHour() + " Min: "
                    + getDurationMinute() + " Priority: " + getPriority();
        }
        if (getTime() == null) {
            return "[" + getSymbol() + "]" + "[" + getStatusIcon() + "] " + getDescription()
                    + " Date: " + getStringDate() + " Priority: " + getPriority();
        }
        return "[" + getSymbol() + "]" + "[" + getStatusIcon() + "] " + getDescription()
                + " Date: " + getStringDate() + " Time: " + getStringTime() + " Priority: " + getPriority();
    }


    /**
     * Gets all the details of the task as a string, for saving into the text file.
     * @return saveString
     * @author jaedonkey
     */
    public String getAllDetailsAsString() {
        StringBuilder list = new StringBuilder();
        list.append(getSymbol());
        list.append(" ");
        list.append(getDescription());
        list.append(" ");
        list.append(getisDone());
        list.append(" ");
        list.append(getPriority().toString());
        list.append(" ");
        list.append(getStringDate());
        list.append(" ");
        list.append(getStringTime());
        list.append(" ");
        list.append(getDurationHour().toString());
        list.append(" ");
        list.append(getDurationMinute().toString());
        list.append(" ");
        list.append(gethasReminder());
        return list.toString();
    }

    /**
     * Calculates the priority of the task based on the user defined priority (high/med/low) as well as
     * the time remaining until the date set for the task.
     * @author jaedonkey
     */
    public void calculateAndSetPriorityScore() {
        long score = 0;
        switch (priority) {
        case high:
            score = 100000000;
            break;
        case medium:
            score = 80000000;
            break;
        case low:
            score = 50000000;
            break;
        default:
            score = 0;
        }


        Date d = new Date();
        long diff = this.date.getTime() - d.getTime();
        score += diff;
        priorityScore = score;
        System.out.println("Task:LOG: Score set " + priorityScore);


    }
}
