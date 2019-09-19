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
    private int durationHour;
    private int durationMinute;
    private boolean hasReminder;

    //----------------------->



    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->


    /**
     * Constructor.
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

    public int getDurationHour() {
        return durationHour;
    }

    public int getDurationMinute() {
        return durationMinute;
    }

    public boolean isHasReminder() {
        return hasReminder;
    }

    public Date getTime() {
        return time;
    }

    //----------------------->




    //***SETTER FUNCTIONS***--------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->
    public void markAsDone() {
        isDone = true;
    }

    public String getDescription() {
        return description;
    }

    public void setDate(String dateInput) {
        System.out.println("DATE INPUT : " + dateInput);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(dateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
        this.date = date;
    }

    public void setDurationHour(int durationHour) {
        this.durationHour = durationHour;
    }

    public void setDurationMinute(int durationMinute) {
        this.durationMinute = durationMinute;
    }

    public void setHasReminder() {
        this.hasReminder = true;
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

    //----------------------->


    /**
     * Prints out the task as a nice string.
     * @return String task-string
     */
    @Override
    public String toString() {
        return "[" + getSymbol() + "]" + "[" + getStatusIcon() + "] " + getDescription();
    }



}
