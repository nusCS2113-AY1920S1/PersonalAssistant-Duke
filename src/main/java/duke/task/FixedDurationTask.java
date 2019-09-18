package duke.task;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FixedDurationTask extends Task {

    public int duration;

    public FixedDurationTask(@JsonProperty("description") String description, @JsonProperty("duration") int duration) {
        super(description);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    @Override
    public String toString() {
        return "  " + duration + " minutes  ";
    }
}
