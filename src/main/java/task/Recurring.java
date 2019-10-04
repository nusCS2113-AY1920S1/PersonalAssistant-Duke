package task;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Tasks which have a deadline.
 * Lists the task name and stores the deadline in Date format.
 */
public class Recurring extends Task {
    protected String by;

    /**
     * Recurring task which will add a new task when the previous task is completed.
     * @param description task description
     * @param by task deadline
     */
    public Recurring(String description, String by, String frequency) {
        super(description);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        try {
            this.dateTime = sdf.parse(by);
        } catch (ParseException e) {
            System.out.println("Please enter date time format correctly: dd/mm/yyyy hhmm");
        }
        this.by = by;
        this.frequency = frequency;
    }

    /**
     * Make a recurring task when reading from storage.
     * @param i isDone status
     * @param description of recurring task
     * @param by dateTime of recurring task
     * @param snooze isSnooze status
     * @param frequency of recurrence
     */
    public Recurring(String i, String description, String by, String snooze, String frequency) {
        super(description);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HHmm");
        try {
            this.dateTime = sdf.parse(by);
        } catch (ParseException e) {
            System.out.println("Please enter date time format correctly: dd/mm/yyyy hhmm");
        }
        this.by = by;
        this.isDone = i.equals("1");
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "[R]" + " [" + frequency + "] " + super.toString() + " (at: " + this.dateTime + ")";
    }

    /**
     * Returns a string that is formatted for the text file.
     * @return String formatted for text file
     */
    @Override
    public String toWriteFile() {
        int boolToInt = isDone ? 1 : 0;
        return "R | "  + boolToInt + " | " + this.description + " | " + this.by + " | " + frequency + "\n";
    }

    @Override
    public Date getBy() {
        return this.dateTime;
    }
    @Override
    public String getDesc() {
        return description;
    }
    @Override
    public String getFreq(){
        return frequency;
    }
}
