package duke.parser;

import duke.exceptions.DukeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.DateTimeException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class DateTimeRecognition {
    private String date;

    public DateTimeRecognition(String date) {
        this.date = date;
    }

    /**
     * This function enables Duke to read dates in a "dd-MM-yyyy HH:mm format".
     * @throws DukeException when the date format is incorrect.
     */
    public void dateTime() throws DukeException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        } catch (DateTimeException e) {
            throw new DukeException(" The format for including date and time for an event/"
                    + "deadline is <ddMM-yyyy HH:mm>");
        }
    }
}