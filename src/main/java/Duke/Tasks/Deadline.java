package Duke.Tasks;

import Duke.Exceptions.DukeInvalidTimeException;
import Duke.Util.DateTimeParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    public Deadline(String... input) {
        super(input[0]);
        setDateAndTime(input[input.length - 1]);
    }

    /**
     * Specific to deadline, the date data has to be stored
     * into LocalDateTime object.
     * @param dateAndTime String date and time associated with the task.
     */
    private void setDateAndTime(String dateAndTime) {
        try {
            super.dateTime = DateTimeParser.getStringToDate(dateAndTime);
        } catch (DukeInvalidTimeException e) {
            System.out.println(e.getMessage() + " " + dateAndTime + getTask());
        }
    }

    @Override
    public String writingFile() { return "D" + "|" + super.writingFile() +
            "|" + dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]")); }

    @Override
    public String toString() {
        return "[D]" + super.toString() +
                " (by: " + dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]")) + ")";
    }


}
