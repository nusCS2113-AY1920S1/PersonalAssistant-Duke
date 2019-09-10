import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Events extends Task {

    protected String at;

    public Events(String description, String at) {
        super(description);
        this.at = at;
    }

    @Override
    public String toString() {
        return "[E]" + super.printStatus() + " (at: " + super.timeFormatter(at) + ")";
    }

    public String txtFormat() {
        return "E | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + super.timeFormatter(at);
    }

    public String writeTxt(){
        return "E | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + this.at;
    }
}