package Tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Events extends Task{

    protected Date at;
    private SimpleDateFormat simpleDateFormat;

    public Events(String description, Date at) {
        super(description);
        this.at = at;
        super.type = "E";
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(at: " + getAt() + ")";
    }

    public String getAt(){
        return simpleDateFormat.format(at);
    }

    public Date getDateAt() {
        return at;
    }
}