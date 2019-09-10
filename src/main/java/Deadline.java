import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {

    protected String by;
    protected LocalDateTime localDateTime;

    public Deadline(String description, String by)
    {
        super(description);
        this.by = by;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        try
        {
            localDateTime = LocalDateTime.parse(by, formatter);
        }
        catch (DateTimeParseException e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.printStatus() + " (by: " + localDateTime + ")";
    }

    public String txtFormat() {
        return "D | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + this.localDateTime;
    }

    public String writeTxt(){
        return "D | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + this.by;
    }



}