package Tasks;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;

import UI.Ui;

public class Event extends Task {
    public LocalDate date;
    public LocalTime start;
    public LocalTime end;

    public Event(String description, String at) {
        super(description);

        try {
            DateTimeFormatter fmtED = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //formatter for date
            DateTimeFormatter fmtET = DateTimeFormatter.ofPattern("HH:mm:ss"); //formatter for time
            //input format for event timing: yyyy-MM-dd HH:mm:ss-HH:Mmm:ss
            String[] dateTime = at.split(" ");
            String[] time = dateTime[1].split("-");

            this.date = LocalDate.parse(dateTime[0], fmtED);
            this.start = LocalTime.parse(time[0], fmtET);
            this.end = LocalTime.parse(time[1], fmtET);
        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException e) {
            Ui.showEventDateFormatError();
        }
    }
    @Override
    public String toString() {
        return "E"+ "|" + super.getStatusIcon() + "| " + super.description + "|" + "at: " + date + " " + start + "-" + end;
    }
    public String listFormat(){
        String datestring = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));// You can change to this format
        return "[E]" + "[" + super.getStatusIcon() + "]" + super.description + "(at:" + date + " " + start + "-" + end + ")";
    }

}


