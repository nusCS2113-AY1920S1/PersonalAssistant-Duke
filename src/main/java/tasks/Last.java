package tasks;

/**
 * Provide support for managing tasks that takes a fixed amount of time but does not have a fixed start/end time
 * e.g., reading the sales report (needs 2 hours).
 */

public class Last extends Task{

    private String duration;

    public Last(){}

    public Last(String description, String duration){
        super(description);
        this.duration = duration;
    }

    public void setDuration(String duration){
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "[L]" + super.toString() + " (last: " + duration + ")";
    }

    @Override
    public String dataString() {

        return "L | " + (this.isDone ? 1 : 0) + " | " + this.description + " | " + this.duration;
    }

}
