import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {

    protected String by;

    public Deadline(String description, String by)
    {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
            return "[D]" + super.printStatus() + " (by: " + super.timeFormatter(by) + ")";
    }

    public String txtFormat() {
        return "D | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + super.timeFormatter(by);
    }

    public String writeTxt(){
        return "D | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + this.by;
    }



}