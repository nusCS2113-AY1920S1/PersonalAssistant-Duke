package Duke.Tasks;

import Duke.Exceptions.DukeInvalidTimeException;
import Duke.Util.DateTimeParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Events extends Task {
    private LocalDateTime dateTime;

    public Events(String... input) {
        super(input[0]);
        dateTime = null;
        setDateAndTime(input[input.length - 1]);
    }

    /**
     * Specific to events, the date data has to be stored
     * into LocalDateTime object.
     * @param dateAndTime String date and time associated with the task.
     */
    private void setDateAndTime(String dateAndTime) {
        try {
            dateTime = DateTimeParser.getStringToDate(dateAndTime);
        } catch (DukeInvalidTimeException e) {
            System.out.println(e.getMessage() + " " + dateAndTime + getTask());
        }
    }

    @Override
    public String writingFile() {
        return "E" + "|" + super.writingFile() +
                "|" + dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (at: " + dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]")) + ")";
    }

}
