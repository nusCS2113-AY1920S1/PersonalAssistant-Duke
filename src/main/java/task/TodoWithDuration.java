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
        String message =  super.getPriorityIcon() + "[T]" + "[" + super.getStatusIcon() + "] " + this.description + " ";
        String timeDetails = "(for " + duration + " hours)";
        message.concat(timeDetails);
        return message;
    }
}
