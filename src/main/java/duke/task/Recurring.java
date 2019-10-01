package duke.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Recurring extends Task {

    protected String frequency;
    protected Date date;
    protected SimpleDateFormat dateFormatter;
    protected String formattedDate;

    /**
     * Constructor for class Period.
     * @param description String containing the description of the task
     * @param frequency String containing the frequency at which the task is recurring at.
     *                  Duke supports daily, weekly and monthly recurring tasks.
     * @param dayOrDate String containing the day or date that a task should be done.
     */
    public Recurring(String description, String frequency, String dayOrDate) throws ParseException {
        super(description); //super class constructor call to the Task(description) constructor
        this.frequency = frequency;
        // refactor with parseAndFormat() method.
        if (frequency.equals("weekly")) {
            date = new SimpleDateFormat("EEEE").parse(dayOrDate);
            dateFormatter = new SimpleDateFormat("EEEE");
            formattedDate = dateFormatter.format(date);
        } else if (frequency.equals("monthly")) {
            date = new SimpleDateFormat("dd").parse(dayOrDate);
            dateFormatter = new SimpleDateFormat("d");
            formattedDate = dateFormatter.format(date);
        }
        // include support for yearly recurring tasks.
        /*
        else if (frequency.equals("yearly")) {
            date = new SimpleDateFormat("dd/MM").parse(dayOrDate); // ParseException here.
            System.out.println("parse year error");
            dateFormatter = new SimpleDateFormat("dd MM");
            formattedDate = dateFormatter.format(date);
        }
        */
    }

    /**
     * Converts user input command to a standardized format to store in file.
     * @return String containing the standardized format
     */
    @Override
    public String toSaveString() {
        if (frequency.equals("daily")) {
            return "R" + super.toSaveString() + " | " + frequency;
        } else {
            return "R" + super.toSaveString() + " | " + frequency + " | " + formattedDate;
        }
    }

    /**
     * Converts user input command to a standardized format in taskList.
     * @return String containing the standardized format
     */
    @Override
    public String toString() {
        if (frequency.equals("daily")) {
            return "[R]" + super.toString() + " (" + frequency + ")";
        } else {
            return "[R]" + super.toString() + " (" + frequency + ": " + formattedDate + ")";
        }
    }

    public Date getDateTime() {
        return null;
    }
}
