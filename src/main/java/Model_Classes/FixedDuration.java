package Model_Classes;

import java.util.Date;

public class FixedDuration extends Event {
    private int duration;
    private Date at;

    /**
     * Constructor for fixed duration
     * @param description Description of event
     * @param at Date of event
     * @param duration duration of event
     */
    public FixedDuration(String description, Date at, int duration) {
        super(description, at);
        this.duration = duration;
    }

    /**
     * Returns duration
     * @return duration of event in DD/MM/YYYY format
     */
    public int getDuration(){
        return duration;
    }

    /**
     * Returns string with format of date and duration
     * @return string containing date and duration of the event
     */
    @Override
    public String toString() {
        return super.toString() + " (done in: " + duration + " hours)"; }
}
