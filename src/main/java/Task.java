import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Task {
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ?  "V" : "x"); //return tick or X symbols
    }

    public void setTime(Date d) {
        //for polymorphism use
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public abstract String dataString();
}
