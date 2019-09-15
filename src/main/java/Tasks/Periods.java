package Tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Periods extends Task {

    protected String from, to;
    private SimpleDateFormat simpleDateFormat;

    public Periods (String Description, String from, String to) {
        super(Description);
        this.from = from;
        this.to = to;
        super.type = "P";
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
    }

    @Override
    public String toString() {
        return "[P]" + super.toString() + "(from: " + from + " to " + to + ")";
    }

    public String getFrom() { return from; }
    public String getTo() { return to; }

    public Date getDateFrom() throws ParseException {
        return simpleDateFormat.parse(from);
    }
    public Date getDateTo() throws ParseException {
        return simpleDateFormat.parse(to);
    }
}
