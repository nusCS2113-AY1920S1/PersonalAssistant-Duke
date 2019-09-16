package duke.command;

import duke.exception.DukeException;
import duke.Storage;
import duke.TaskList;
import duke.Ui;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a <code>Command</code> object that corresponds to specific commands
 * defined by the user.
 * Abstract parent of all other <code>Command</code> subclasses.
 */
public abstract class Command {

    /**
     * Invokes other <code>Command</code> subclasses based on the input given by the user.
     * @param tasks Instance of <code>TaskList</code> that stores <code>Task</code> objects.
     * @param ui Instance of <code>Ui</code> that is responsible for visual feedback.
     * @param storage Instance of <code>Storage</code> that enables the reading and writing of <code>Task</code>
     *      *         objects to harddisk.
     * @throws DukeException Catches invalid commands given by user.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException;

    /**
     * Parses the <code>Timestamp</code> given by the user and returns the parsed
     * <code>date</code> as a string if the <code>date</code> is valid.
     * @param date <code>Timestamp</code> supplied by user.
     * @return Parsed <code>Timestamp</code> if the <code>Timestamp</code> is valid.
     */
    public String parseTimeStamp(String date) {
        String str = "failed";
        try {
            SimpleDateFormat format = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
            format.parse(date);
            String pattern = "dd-MM-yyyy HH:mm";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(date));
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            DateTimeFormatter formatter2 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            str = formatter2.format(timestamp.toLocalDateTime());
            return str;
        } catch (ParseException | DateTimeException e) {
            System.out.println("Timestamp given is invalid! Please try again.");
            return str;
        }
    }

    /**
     * Checks if <code>ExitCommand</code> is called for <code>Duke</code>
     * to terminate.
     * @return true if <code>ExitCommand</code> is called, false otherwise.
     */
    public abstract boolean isExit();
}