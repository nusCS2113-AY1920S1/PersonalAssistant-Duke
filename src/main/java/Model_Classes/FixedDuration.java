package Model_Classes;

import Enums.TimeUnit;

import java.util.Date;

public class FixedDuration extends Event {
    private final TimeUnit timeUnit;
    private int duration;


    public FixedDuration(String description, Date at, int duration, TimeUnit timeUnit) {
        super(description, at);
        this.duration = duration;
        this.timeUnit = timeUnit;
    }

    public Date getDate() {
        return at;
    }

    public int getDuration(){
        return duration;
    }


    @Override
    public String toString() {
        return super.toString() + " (done in: " + duration + " " + timeUnit + " ) ";
    }
}
