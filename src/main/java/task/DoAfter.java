package task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class DoAfter extends Task {
    protected String after;

    public DoAfter(String description, String after) {
        super(description);
        this.after = after;
    }

    public DoAfter(String i, String description, String after) {
        super(description);
        this.after = after;
        this.isDone = i.equals("1");
    }

    @Override
    public String toString() {
        return "[DA]" + super.toString() + " (after: " + this.after + ")";
    }

    @Override
    public String toWriteFile() {
        int boolToInt = isDone ? 1 : 0;
        return "DA | " + boolToInt + " | " + this.description + " | " + this.after +  " | " + "\n";
    }
}
