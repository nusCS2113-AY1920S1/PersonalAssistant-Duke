package duke.tasks;

import duke.exceptions.DukeInvalidTimeException;
import duke.util.DateTimeParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
    
public class Deadline extends Task {
    private LocalDateTime dateTime;

    /**
     * Constructor for Deadline class, using String Varargs.
     * @param input Parsed user string input, first input being name,
     *              second input being the date.
     */
    public Deadline(String... input) {
        super(input[0]);
        dateTime = null;
        setDateAndTime(input[input.length - 1]);
    }

    /**
     * Specific to deadline, the date data has to be stored
     * into LocalDateTime object.
     * @param dateAndTime String date and time associated with the task.
     */
    private void setDateAndTime(String dateAndTime) {
        try {
            dateTime = DateTimeParser.getStringToDate(dateAndTime);
        } catch (DukeInvalidTimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String writingFile() {
        return "D"
            + "|"
            + super.writingFile()
            + "|"
            + dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"));
    }

    @Override
    public String toString() {
        return "[D]"
            + super.toString()
            + " (by: "
            + dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH:mm]"))
            + ")";
    }


}
