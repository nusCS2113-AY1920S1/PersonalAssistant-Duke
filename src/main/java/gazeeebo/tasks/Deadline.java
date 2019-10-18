package gazeeebo.tasks;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    public LocalDateTime by ;
    public static DateTimeFormatter fmtD = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//24h clock

    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDateTime.parse(by, fmtD);
    }

    @Override
    public String toString() {
        return "D"+ "|" + super.getStatusIcon() + "|" + super.description + "|" + "by: " + by.format(fmtD);
    }

    @Override
    public String listFormat(){
        return "[D]" + "[" + super.getStatusIcon() + "] " + super.description + "(by:" + by.format(DateTimeFormatter.ofPattern("dd LLL yyyy HH:mm:ss")) + ")";
    }

}