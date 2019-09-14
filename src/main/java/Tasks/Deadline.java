package Tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Deadline extends Task{

    protected Date by;
    private SimpleDateFormat simpleDateFormat;

    public Deadline(String description, Date by) {
        super(description);
        this.by = by;
        super.type = "D";
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + getBy() + ")";
    }

    public String getBy(){
        return simpleDateFormat.format(by);
    }

    public Date getDateBy(){
        return by;
    }


}
