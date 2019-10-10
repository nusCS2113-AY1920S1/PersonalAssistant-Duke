package task;

import java.time.Period;

public class TodoWithDuration extends Todo {
    public int duration;

    public TodoWithDuration(String description, int duration) {
        super(description);
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "[T]" + "[" + super.getStatusIcon() + "] " + this.description + " " + "(for " + duration + " hours)";
    }
}
