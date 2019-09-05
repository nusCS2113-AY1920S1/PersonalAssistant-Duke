import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeRecognition {
    private String date;

    public DateTimeRecognition(String date) {
        this.date = date;
    }

    public void dateTime() throws DukeException {
        try {
            SimpleDateFormat identifyFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
            Date dateAndTime = identifyFormat.parse(date);
        } catch (ParseException e) {
            throw new DukeException(" The format for including date and time for an event/"
                    + "deadline is <dd/mm/yyyy HHmm>");
        }
    }
}