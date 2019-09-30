package oof.command;

import oof.exception.OofException;
import oof.Storage;
import oof.TaskList;
import oof.Ui;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Command object that corresponds to specific commands
 * defined by the user.
 * Abstract parent of all other Command subclasses.
 */
public abstract class Command {

    /**
     * Invokes other Command subclasses based on the input given by the user.
     *
     * @param tasks   Instance of TaskList that stores Task objects.
     * @param ui      Instance of Ui that is responsible for visual feedback.
     * @param storage Instance of Storage that enables the reading and writing of Task
     *                objects to hard disk.
     * @throws OofException Catches invalid commands given by user.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws OofException;

    /**
     * Parses the Timestamp given by the user and returns the parsed
     * date as a string if the date is valid.
     *
     * @param date Timestamp supplied by user.
     * @return Parsed Timestamp if the Timestamp is valid, else returns "failed".
     */
    public String parseTimeStamp(String date) {
        try {
            SimpleDateFormat format = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
            format.parse(date);
            String pattern = "dd-MM-yyyy HH:mm";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(date));
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            DateTimeFormatter formatter2 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            return formatter2.format(timestamp.toLocalDateTime());
        } catch (ParseException | DateTimeException e) {
            return "failed";
        }
    }

    /**
     * Checks if ExitCommand is called for Oof to terminate.
     *
     * @return true if ExitCommand is called, false otherwise.
     */
    public abstract boolean isExit();
}