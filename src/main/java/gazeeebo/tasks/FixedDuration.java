package gazeeebo.tasks;
public class FixedDuration extends Task {
    public String duration;
    public FixedDuration(String description, String duration) {
        super(description);
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "FD"+ "|" + super.getStatusIcon() + "|" + super.description + "|" + duration;
    }
    @Override
    public String listFormat(){
        return "[FD]" + "[" + super.getStatusIcon() + "] " + super.description + "(requires:" + duration + ")";
    }

}