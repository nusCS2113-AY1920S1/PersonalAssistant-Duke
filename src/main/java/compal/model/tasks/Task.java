package compal.model.tasks;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents task with description, status and reminder.
 */
public abstract class Task implements Serializable {

    public boolean isDone;

    //***Class Properties/Variables***--------------------------------------------------------------------------------->
    protected String symbol;
    private String description;
    private Date date;   //For now, we only process dates in the format dd/mm/yyyy hhmm. See TaskList class for details
    private Date startTime;
    private Date endTime;
    private boolean hasReminder;
    private Priority priority;
    private long priorityScore;
    private int id;


    /**
     * Constructs Task object.
     *
     * @param description Description.
     */
    protected Task(String description, Priority priority) {
        this.description = description;
        this.priority = priority;
        this.isDone = false;
        hasReminder = false;

    }


    /**
     * Gets priority status (HIGH, MEDIUM, LOW) of task.
     *
     * @return Priority status of task.
     */
    public Priority getPriority() {
        return priority;
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
     * Gets/returns task id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets task id.
     */
    public void setId(int id) {
        this.id = id;
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
     * Gets status icon (tick or cross) of task in String.
     *
     * @return Status icon (tick or cross) of task.
     */
    public String getStringisDone() {
        return (isDone ? "true" : "false");
    }

    /**
     * Gets status icon (tick or cross) of task in Boolean.
     *
     * @return Status icon (tick or cross) of task.
     */
    public Boolean getisDone() {
        return (isDone);
    }

    /**
     * Gets status icon (tick or cross) of task.
     *
     * @return Status icon (tick or cross) of task.
     */
    public Boolean gethasReminder() {
        return (hasReminder);
    }

    /**
     * Gets status icon (tick or cross) of task.
     *
     * @return Status icon (tick or cross) of task.
     */
    public String getStringhasReminder() {
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
     * Sets symbol of task to be symbolInput.
     *
     * @param symbolInput The symbol for the task.
     */
    public void setSymbol(String symbolInput) {
        symbol = symbolInput;
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


    /**
     * Gets hasReminder of task.
     *
     * @return whether the task has reminder. If task has reminder, return true. Else false.
     */
    public boolean hasReminder() {
        return hasReminder;
    }

    /**
     * Gets start time of task in date format.
     *
     * @return Time of task.
     */
    public Date getStartTime() {
        if (getStringStartTime().equals("-")) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String startTime = getStringStartTime();
        int hour = Integer.parseInt(startTime.substring(0, 2));
        int min = Integer.parseInt(startTime.substring(2, 4));
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, min);
        return calendar.getTime();
    }

    /**
     * Formats start timeInput then sets time as timeInput.
     *
     * @param timeInput Input time of task.
     */
    public void setStartTime(String timeInput) {
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        Date time = null;
        try {
            time = format.parse(timeInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.startTime = time;
    }

    /**
     * Gets start time of task in string.
     *
     * @return Time of task.
     */
    public String getStringStartTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HHmm");
        if (this.startTime == null) {
            return "-";
        } else {
            return formatter.format(this.startTime);
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
     * Sets the description of task.
     */
    public void setDescription(String newDescription) {
        this.description = newDescription;
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
     * Sets HasReminder.
     */
    public void setHasReminder(Boolean status) {
        this.hasReminder = status;
    }

    /**
     * Returns the task as a formatted string.
     * This function standardizes the displayed task format.
     *
     * @return Task as a formatted string.
     */
    @Override
    public String toString() {
        int strCase = 0;
        if (getStringStartTime().equals("-")) {
            strCase = 1;
        }

        switch (strCase) {
        case 1:
            return "\n" + "[" + getSymbol() + "]" + "[" + getStatusIcon() + "] " + getDescription()
                + " \nTask ID:" + getId()
                + " \nDate: " + getStringDate() + " \nEnd Time: " + getStringEndTime()
                + " \nPriority: " + getPriority() + "\n***************";
        default:
            return "\n" + "[" + getSymbol() + "]" + "[" + getStatusIcon() + "] " + getDescription()
                + " \nTask ID:" + getId()
                + " \nDate: " + getStringDate() + " \nStart Time: " + getStringStartTime()
                + " \nEnd Time: " + getStringEndTime() + " \nPriority: " + getPriority()
                + "\n***************";
        }


    }

    //@@author jaedonkey
    /**
     * Gets all the details of the task as a string, for saving into the text file.
     *
     * @return saveString
     */
    public String getAllDetailsAsString() {
        StringBuilder list = new StringBuilder();
        list.append(getId());
        list.append("_");
        list.append(getSymbol());
        list.append("_");
        list.append(getDescription());
        list.append("_");
        list.append(getStringisDone());
        list.append("_");
        list.append(getPriority().toString());
        list.append("_");
        list.append(getStringDate());
        list.append("_");
        list.append(getStringStartTime());
        list.append("_");
        list.append(getStringEndTime());
        list.append("_");
        list.append(getStringhasReminder());
        return list.toString();
    }

    //@@author jaedonkey
    /**
     * Calculates the priority of the task based on the user defined priority (high/med/low) as well as
     * the time remaining until the date set for the task.
     *
     * @deprecated not in use for now
     */
    @Deprecated
    public void calculateAndSetPriorityScore() {
        long score;
        switch (priority) {

        case high:
            score = 100;
            break;
        case medium:
            score = 50;
            break;
        case low:
            score = 20;
            break;
        default:
            score = 0;

        }

        Date d = new Date();
        long diff = d.getTime() - this.date.getTime();
        long diffHours = diff / (60 * 60 * 1000);
        //System.out.println("Task:LOG: Difference is " + diffHours);
        score += diffHours;
        this.priorityScore = score;
    }

    /**
     * Gets the end time for the task.
     *
     * @return date end time
     */
    public Date getEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String endTime = getStringEndTime();
        int hour = Integer.parseInt(endTime.substring(0, 2));
        int min = Integer.parseInt(endTime.substring(2, 4));
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, min);
        return calendar.getTime();
    }

    /**
     * Formats end timeInput then sets end time as timeInput.
     *
     * @param timeInput Input time of task.
     */
    public void setEndTime(String timeInput) {
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        Date time = null;
        try {
            time = format.parse(timeInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.endTime = time;
    }

    /**
     * Gets end time of task in string.
     *
     * @return Time of task.
     */

    public String getStringEndTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HHmm");
        if (this.endTime == null) {
            return "-";
        } else {
            return formatter.format(this.endTime);
        }
    }

    /**
     * Create string of date and start time.
     *
     * @return string object of date and start time.
     */
    public String getStringDateAndStartTime() {
        return getStringDate() + " " + getStringStartTime();
    }

    /**
     * Create a string of date and end time.
     *
     * @return string object of date and end time.
     */
    public String getStringDateAndEndTime() {
        return getStringDate() + " " + getStringEndTime();
    }


    /**
     * Create a date object of date and start time.
     *
     * @return date object of formatted time.
     */
    public Date getDateObgDateAndStartTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        try {
            Date date = sdf.parse(getStringDateAndStartTime());
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create a date object of date and end time.
     *
     * @return date object of formatted time.
     */
    public Date getDateObgDateAndEndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        try {
            Date date = sdf.parse(getStringDateAndEndTime());
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public enum Priority {
        high, medium, low
    }


}
