package javacake.tasks;

import javacake.exceptions.DukeException;

import java.util.Calendar;
import java.util.Date;

public class RecurringTask extends ToDo {
    private Date date;
    protected Frequency frequency;
    protected String freq;

    public enum Frequency {
        DAILY, WEEKLY, MONTHLY
    }

    /**
     * Constructor for recurring tasks.
     * @param description Task description
     * @param startDateTime Starting date of task
     * @param frequency daily, weekly or monthly
     */
    public RecurringTask(String description, Date startDateTime, String frequency) {
        super(description);

        this.date = startDateTime;

        try {
            setFrequency(frequency);
        } catch (DukeException e) {
            //e.getMessage();
        }

        //update date if it is past current date
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        while (date.before(currentDate)) {
            unmark();
            c.setTime(date);
            switch (this.frequency) {
            case DAILY:
                c.add(Calendar.DATE, 1);
                break;
            case WEEKLY:
                c.add(Calendar.WEEK_OF_YEAR, 1);
                break;
            case MONTHLY:
                c.add(Calendar.MONTH, 1);
                break;
            default:
            }
            date = c.getTime();
        }
    }

    private void setFrequency(String freq) throws DukeException {
        switch (freq) {
        case "daily":
            this.frequency = Frequency.DAILY;
            this.freq = "d";
            break;
        case "weekly":
            this.frequency = Frequency.WEEKLY;
            this.freq = "w";
            break;
        case "monthly":
            this.frequency = Frequency.MONTHLY;
            this.freq = "m";
            break;
        default:
            throw new DukeException("Please enter a frequency: daily, weekly or monthly");
        }
    }

    @Override
    public String toString() {
        return ("[" + freq + "]" + description);
    }

    @Override
    public String getFullString() {
        return ("[R][" + freq + "]" + "[" + getStatusIcon() + "] " + description + " (at: " + date + ")");
    }

    @Override
    public Date getDateTime() {
        return this.date;
    }

    @Override
    public String getExtra() {
        return this.date.toString();
    }

    /**
     * Method to get frequency.
     * @return Frequency type
     */
    public Frequency getFrequency() {
        return this.frequency;
    }
}
