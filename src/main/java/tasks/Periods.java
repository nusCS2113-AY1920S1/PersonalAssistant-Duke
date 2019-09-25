package tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Periods extends Task {

    protected String from;
    protected String to;
    private SimpleDateFormat simpleDateFormat;

    public Periods(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
        super.type = "P";
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
    }

    @Override
    public String toString() {
        return "[P]" + super.toString() + "(from: " + from + " to " + to + ")";
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Date getDateFrom() throws ParseException {
        return simpleDateFormat.parse(from);
    }

    public Date getDateTo() throws ParseException {
        return simpleDateFormat.parse(to);
    }
}
