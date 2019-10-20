package gazeeebo.tasks;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    public LocalDate date;
    public LocalTime start;
    public LocalTime end;

    public static DateTimeFormatter fmtED = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //formatter for date
    public static DateTimeFormatter fmtET = DateTimeFormatter.ofPattern("HH:mm:ss"); //formatter for time

    public Event(String description, String at) throws DateTimeParseException, ArrayIndexOutOfBoundsException{
        super(description);
        String[] dateTime = at.split(" ");
        String[] time = dateTime[1].split("-");

        this.date = LocalDate.parse(dateTime[0], fmtED);
        this.start = LocalTime.parse(time[0], fmtET);
        this.end = LocalTime.parse(time[1], fmtET);
    }
    @Override
    public String toString() {
        return "E"+ "|" + super.getStatusIcon() + "|" + super.description + "|" + "at: " + fmtED.format(date)
                + " " + this.start.format(fmtET) + "-" + this.end.format(fmtET);
    }
    public String listFormat(){
        String dateString = date.format(DateTimeFormatter.ofPattern("dd LLL yyyy"));// You can change to this format

        return "[E]" + "[" + super.getStatusIcon() + "]" + super.description + "(at:" + dateString + " "
                + this.start.format(fmtET) + "-" + this.end.format(fmtET) + ")";

    }

}