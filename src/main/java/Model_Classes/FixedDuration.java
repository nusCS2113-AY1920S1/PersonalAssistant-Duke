package Model_Classes;

import java.util.Date;

public class FixedDuration extends Meeting {
    private String duration;
    private String unit;

    /**
     * Constructor for fixed duration
     * @param description Description of meeintg
     * @param at Date of meeting
     * @param duration Duration of meeting
     */
    public FixedDuration(String description, Date at, String duration, String unit) {
        super(description, at);
        this.duration = duration;
        this.unit = unit;
    }

    /**
     * Returns duration
     * @return duration of meeting in DD/MM/YYYY format
     */
    public String getDuration(){
        return duration;
    }

    /**
     * Return time unit of the duration of meeting
     * @return time unit of the duration of meeting
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Returns string with format of date and duration
     * @return string containing date and duration of the meeting
     */
    @Override
    public String toString() {
        return super.toString() + " (done in: " + duration + " " + unit + ")";
    }
}
