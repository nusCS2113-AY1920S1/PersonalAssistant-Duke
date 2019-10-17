package duke.parser;

import duke.exceptions.DukeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.DateTimeException;

public class DateTimeRecognition {
    private String date;

    public DateTimeRecognition(String date) {
        this.date = date;
    }

    /**
     * This function enables Duke to read dates in a "dd-MM-yyyy HH:mm format".
     *
     * @throws DukeException when the date format is incorrect.
     */
    public void dateTime() throws DukeException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        } catch (DateTimeException e) {
            throw new DukeException(" The format for including date and time for an event/"
                    + "deadline is <dd/MM/yyyy HH:mm>");
        }
    }
}
