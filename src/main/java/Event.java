public class Event extends Task {

    protected String at;

    public Event(String description, String at) {
        super(description);
        this.at = at;
    }

    public String getType() {
        return "[E]";
    }

    public String getBy() {
        return this.at;
    }

    public String getStatusIcon() {
        return "[E]" + "[" + (isDone ? "\u2713" : "\u2718") + "] " + this.description + " (at: " + at + ")";
    }
}
