import java.util.Date;

public class Event extends Task {
    protected Date at ;

    public Event(String description, Date at) {
        super(description);
        this.at = at;
    }

    @Override
    public String toString() {
        return "E"+ "|" + super.getStatusIcon() + "| " + super.description + "|" + "at: "+at;
    }
    public String listformat(){
        return "[E]" + "[" + super.getStatusIcon() + "]" + super.description + "(at:" + at + ")";
    }
}


