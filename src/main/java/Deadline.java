import java.text.SimpleDateFormat;
import java.util.Date;

public class Deadline extends Task {
    @Override
    public void setTime(Date by) {
        this.by = by;
    }

    protected Date by;

    public Deadline(String description, Date by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    @Override
    public String dataString() {
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hhmm");
        return "D | " + (this.isDone ? 1 : 0) + " | " + this.description + " | " + ft.format(this.by);
    }
}
