package compal.tasks;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Task implements Serializable {


    //***Class Properties/Variables***--------------------------------------------------------------------------------->

    public boolean isDone;
    protected String symbol;
    private int id;

    //for now, we only process dates in the format dd/mm/yyyy hhmm. See TaskList class for details
    private Date date;
    private Date time;
    private String taskType;
    private String description;
    private Integer durationHour;
    private Integer durationMinute;
    private boolean hasReminder;

    //----------------------->


    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->


    /**
     * Constructor.
     *
     * @param description the task's description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        hasReminder = false;
    }

    //----------------------->


    //***GETTER FUNCTIONS***--------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }

    public String getSymbol() {
        return symbol;
    }

    public Date getDate() {
        return this.date;
    }

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

    public String getStringDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate = formatter.format(this.date);
        return stringDate;
    }

    public Integer getDurationHour() {
        return durationHour;
    }

    public void setDurationHour(Integer durationHour) {
        this.durationHour = durationHour;
    }

    public Integer getDurationMinute() {
        return durationMinute;
    }

    //----------------------->

    public void setDurationMinute(Integer durationMinute) {
        this.durationMinute = durationMinute;
    }

    public boolean isHasReminder() {
        return hasReminder;
    }

    public Date getTime() {
        return time;
    }

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

    public String getStringTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HHmm");
        String stringDate = formatter.format(this.time);
        return stringDate;
    }

    //***SETTER FUNCTIONS***--------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->
    public void markAsDone() {
        isDone = true;
    }

    public String getDescription() {
        return description;
    }

    public void setHasReminder() {
        this.hasReminder = true;
    }

    //----------------------->

    /**
     * Prints out the task as a nice string.
     *
     * @return String task-string
     */
    @Override
    public String toString() {

        if (getDurationHour() != null && getDurationMinute() != null) {
            return "[" + getSymbol() + "]" + "[" + getStatusIcon() + "] " + getDescription() + " Date: " + getStringDate() + " Hour: " + getDurationHour() + " Min: " + getDurationMinute();
        }

        if (getTime() == null) {
            return "[" + getSymbol() + "]" + "[" + getStatusIcon() + "] " + getDescription() + " Date: " + getStringDate();
        }

        return "[" + getSymbol() + "]" + "[" + getStatusIcon() + "] " + getDescription() + " Date: " + getStringDate() + " Time: " + getStringTime();
    }


}
