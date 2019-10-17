package duke.parser;

import duke.exceptions.DukeException;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class DateDoWithin {
    private String date;

    public DateDoWithin(String date) {
        this.date = date;
    }

    /**
     * This function enables Duke to read dates in a "dd/MM/yyyy format".
     * @throws DukeException when the date format is incorrect.
     */
    public void dateTimer() throws DukeException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dateTime = LocalDate.parse(date, formatter);

        } catch (DateTimeException e) {
            throw new DukeException(" The format for including date and time for a do within is <dd/MM/yyyy>");
        }
    }
}