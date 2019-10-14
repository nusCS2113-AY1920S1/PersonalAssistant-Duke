package task;

public class TodoWithDuration extends Todo {
    public int duration;

    public TodoWithDuration(String description, int duration) {
        super(description);
        this.duration = duration;
    }

    @Override
    public String toString() {
        String message = super.getPriorityIcon() + "[T]" + "[" + super.getStatusIcon() + "] " + this.description + " ";
        String timeDetails = "(for " + duration + " hours)";
        if (!comment.isBlank()) {
            timeDetails = timeDetails + "  Note to self: " + comment;
        }
        return message.concat(timeDetails);
    }
}
