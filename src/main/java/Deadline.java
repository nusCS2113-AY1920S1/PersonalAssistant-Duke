import java.text.SimpleDateFormat;
import java.util.Date;

public class Deadline extends Task {
    protected Date by ;

    public Deadline(String description, Date by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "D"+ "|" + super.getStatusIcon() + "| " + super.description + "|" + "by: " + by;
    }

    @Override
    public String listformat(){
        return "[D]" + "[" + super.getStatusIcon() + "]" + super.description + "(by:" + by + ")";
    }
}
