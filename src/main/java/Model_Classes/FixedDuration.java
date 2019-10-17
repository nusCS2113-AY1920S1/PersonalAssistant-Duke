package Model_Classes;

import java.util.Date;

public class FixedDuration extends Meeting {
    private int duration;
    private String unit;
    private Date at;

    /**
     * Constructor for fixed duration
     * @param description Description of event
     * @param at Date of event
     * @param duration Duration of event
     */
    public FixedDuration(String description, Date at, int duration, String unit) {
        super(description, at);
        this.duration = duration;
        this.unit = unit;
    }

    /**
     * Returns duration
     * @return duration of event in DD/MM/YYYY format
     */
    public int getDuration(){
        return duration;
    }

    public String getUnit() {
        return unit;
    }

    /**
     * Returns string with format of date and duration
     * @return string containing date and duration of the event
     */
    @Override
    public String toString() {
        return super.toString() + " (done in: " + duration + " " + unit + ")";
    }
}
