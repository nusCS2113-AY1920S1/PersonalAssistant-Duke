import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected LocalDateTime ld;


    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    abstract String txtFormat();

    abstract String writeTxt();

    public void markAsDone()
    {
        isDone = true;
    }

    public String printStatus()
    {
        return "[" + this.getStatusIcon() + "] " + description;
    }

    public String getDescription(){
        return description;
    }

    public String timeFormatter(String timeBeforeFormat)  {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        DateTimeFormatter stFormatter = DateTimeFormatter.ofPattern("d'st of' MMMM yyyy , ha");
        DateTimeFormatter ndFormatter = DateTimeFormatter.ofPattern("d'nd of' MMMM yyyy , ha");
        DateTimeFormatter rdFormatter = DateTimeFormatter.ofPattern("d'rd of' MMMM yyyy , ha");
        DateTimeFormatter thFormatter = DateTimeFormatter.ofPattern("d'th of' MMMM yyyy , ha");

        ld = LocalDateTime.parse(timeBeforeFormat,parser);

        String output;

        if ((ld.getDayOfMonth()%10) == 1){
            output = ld.format(stFormatter);
        }else if ((ld.getDayOfMonth()%10) == 2) {
            output = ld.format(ndFormatter);
        }else if ((ld.getDayOfMonth()%10) == 3) {
            output = ld.format(rdFormatter);
        }else{
            output = ld.format(thFormatter);;
        }

        return output;

    }


}