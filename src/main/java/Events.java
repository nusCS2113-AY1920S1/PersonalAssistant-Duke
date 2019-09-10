import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Events extends Task {

    protected String at;
    protected LocalDateTime localDateTime;

    public Events(String description, String at) {
        super(description);
        this.at = at;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        try
        {
            localDateTime = LocalDateTime.parse(at, formatter);
        }
        catch (DateTimeParseException e)
        {
            System.out.println("Wrong date and time format!");
        }

    }

    @Override
    public String toString() {
        return "[E]" + super.printStatus() + " (at: " + localDateTime + ")";
    }

    public String txtFormat() {
        return "E | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + this.localDateTime;
    }

    public String writeTxt(){
        return "E | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + this.at;
    }
}