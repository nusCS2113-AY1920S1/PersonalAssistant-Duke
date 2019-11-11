package compal.model.tasks;

import compal.commons.CompalUtils;
import compal.commons.LogUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Represents task with description, status and reminder.
 */
public abstract class Task implements Serializable {

    public boolean isDone;

    //***Class Properties/Variables***--------------------------------------------------------------------------------->
    protected String symbol;
    private String description;
    private Date mainDate;
    private Date trailingDate;
    private Date startTime;
    private Date endTime;
    private boolean hasReminder;
    private Priority priority;
    private int id;
    private static final Logger logger = LogUtils.getLogger(Task.class);


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
     * Gets date of task in date format.
     *
     * @return Date of task.
     */
    public Date getMainDate() {
        return this.mainDate;
    }

    /**
     * Formats dateInput then sets date as mainDate.
     *
     * @param dateInput Input date of task.
     */
    public void setMainDate(String dateInput) {
        Date mainDate = CompalUtils.stringToDate(dateInput);
        this.mainDate = mainDate;
    }

    /**
     * Gets mainDate of task in string.
     *
     * @return mainDate of task.
     */
    public String getStringMainDate() {
        String mainDateString = CompalUtils.dateToString(this.mainDate);
        return mainDateString;
    }

    /**
     * Gets trailingDate of Event in date format.
     *
     * @return trailingDate. Used for Event object only.
     */
    public Date getTrailingDate() {
        return this.trailingDate;
    }

    /**
     * Formats dateInput then sets date as trailingDate.
     *
     * @param dateInput Input date for event. Used as the end date for event.
     */
    public void setTrailingDate(String dateInput) {
        this.trailingDate = CompalUtils.stringToDate(dateInput);
    }

    /**
     * Gets trailingDate of task in string.
     *
     * @return trailingDate of task.
     */
    public String getStringTrailingDate() {
        if (this.trailingDate == null) {
            return "-";
        } else {
            String trailingDateString = CompalUtils.dateToString(this.trailingDate);
            return trailingDateString;
        }
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

    //@@author Catherinetan99

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
        calendar.setTime(mainDate);
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
            logger.severe("Invalid start time input receive from tasks.txt!");
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
     * Sets isDone as true.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Sets isDone as false.
     */
    public void markAsNotDone() {
        isDone = false;
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
        if (!getStringTrailingDate().equals("-") && !getStringMainDate().equals(getStringTrailingDate())) {
            strCase = 2;
        }

        switch (strCase) {
        case 1:
            return "\n" + " \nTask ID:" + getId() + "\n"
                + "[" + getSymbol() + "]" + "[" + getStatusIcon() + "] " + getDescription()
                + " \nDate: " + getStringMainDate() + " \nEnd Time: " + getStringEndTime()
                + " \nPriority: " + getPriority() + "\n***************";
        case 2:
            return "\n" + " \nTask ID:" + getId() + "\n"
                + "[" + getSymbol() + "]" + "[" + getStatusIcon() + "] " + getDescription()
                + " \nStart Date: " + getStringMainDate() + " \nStart Time: " + getStringStartTime()
                + "\nEnd Date: " + getStringTrailingDate()
                + " \nEnd Time: " + getStringEndTime() + " \nPriority: " + getPriority()
                + "\n***************";
        default:
            return "\n" + " \nTask ID:" + getId() + "\n"
                + "[" + getSymbol() + "]" + "[" + getStatusIcon() + "] " + getDescription()
                + " \nDate: " + getStringMainDate() + " \nStart Time: " + getStringStartTime()
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
        list.append(getStringMainDate());
        list.append("_");
        list.append(getStringTrailingDate());
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
        long diff = d.getTime() - this.mainDate.getTime();
        long diffHours = diff / (60 * 60 * 1000);
        //System.out.println("Task:LOG: Difference is " + diffHours);
        score += diffHours;
    }

    //@@author Catherinetan99

    /**
     * Gets the end time for the task.
     *
     * @return date end time
     */
    public Date getEndTime() {
        Calendar calendar = Calendar.getInstance();
        if (getStringTrailingDate().equals("-")) {
            calendar.setTime(mainDate);
        } else {
            calendar.setTime(trailingDate);
        }
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
            logger.severe("Invalid end time input receive from tasks.txt!");
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
     * Create a string of date and end time.
     *
     * @return string object of date and end time.
     */
    public String getStringDateAndEndTime() {
        return getStringMainDate() + " " + getStringEndTime();
    }


    /**
     * Create a string of main or trailing date with end time.
     *
     * @return string object of date and end time.
     */
    public String getStringMainOrTrailingDateAndEndTime() {
        return getStringMainOrTrailingDate() + " " + getStringEndTime();
    }

    /**
     * Create a string of main or trailing date.
     *
     * @return string object of date and end time.
     */
    public String getStringMainOrTrailingDate() {
        if (this.trailingDate == null) {
            return getStringMainDate();
        } else {
            String trailingDateString = CompalUtils.dateToString(this.trailingDate);
            return trailingDateString;
        }
    }


    /**
     * Create a date object of date and start time.
     *
     * @return date object of formatted time.
     */
    public Date getDateObgMainDateAndStartOrEndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        try {
            Date date = sdf.parse(getStringDateAndStartOrEndTime());
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create a string main date and start or end time.
     *
     * @return string object of date and end time.
     */
    public String getStringDateAndStartOrEndTime() {
        return getStringMainDate() + " " + getStringStartOrEnd();
    }

    /**
     * Gets start time of task in string.
     *
     * @return Time of task.
     */
    public String getStringStartOrEnd() {
        SimpleDateFormat formatter = new SimpleDateFormat("HHmm");
        if (this.startTime == null) {
            return getStringEndTime();
        } else {
            return formatter.format(this.startTime);
        }
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
