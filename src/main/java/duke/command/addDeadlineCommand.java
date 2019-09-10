package duke.command;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Task;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a <code>Command</code> that appends a new <code>Deadline</code>
 * object to the <code>TaskList</code>.
 */
public class addDeadlineCommand extends Command {

    String line;

    /**
     * Constructor for <code>addDeadlineCommand</code>.
     * @param line Command inputted by user for processing.
     */
    public addDeadlineCommand(String line) {
        super();
        this.line = line;
    }

    /**
     * Performs a series of three main tasks.
     * Processes the command inputted by user into <code>description</code> and <code>date</code>.
     * Checks for the validity of the format of <code>date</code>.
     * Adds a <code>Deadline</code> object to the <code>TaskList</code>
     * and prints the object added before calling methods in <code>Storage</code> to
     * store the object added in the harddisk.
     * @param arr Instance of <code>TaskList</code> that stores <code>Task</code> objects.
     * @param ui Instance of <code>Ui</code> that is responsible for visual feedback.
     * @param storage Instance of <code>Storage</code> that enables the reading and writing of <code>Task</code>
     *                objects to harddisk.
     * @throws DukeException Catches invalid commands given by user.
     */
    public void execute(TaskList arr, Ui ui, Storage storage) throws DukeException {
        String linesplit[] = line.split("/by");
        if (linesplit.length == 1) {
            throw new DukeException("\u2639 OOPS!!! The description of a deadline needs a due date.");
        }
        String start = linesplit[0].trim();
        String end = linesplit[1].trim();
        if (end.length() == 0) {
            throw new DukeException("\u2639 OOPS!!! The datetime of a deadline cannot be empty.");
        }
        else if (isTimeStampValid(end)) {
            String pattern = "dd-MM-yyyy HH:mm";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(end));
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            DateTimeFormatter formatter2 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            end = formatter2.format(timestamp.toLocalDateTime());
            Task task = new Deadline(start, end);
            arr.addTask(task);
            ui.addTaskMessage(task, arr.getSize());
            storage.writeToFile(arr);
        }
        else {
            System.out.println("Time format is wrong! Try again.");
        }
    }

    /**
     * Checks for the validity of the datetime given by user
     * by checking the format and validity of the datetime itself.
     * @param inputString datetime input by user in <code>String</code> format.
     * @return Returns true if the datetime given by user is valid, false otherwise.
     */
    public boolean isTimeStampValid(String inputString)
    {
        SimpleDateFormat format = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");
        try{
            String str = "";
            format.parse(inputString);
            String pattern = "dd-MM-yyyy HH:mm";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(inputString));
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            DateTimeFormatter formatter2 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            str = formatter2.format(timestamp.toLocalDateTime());
            return true;
        }
        catch(ParseException | DateTimeException e)
        {
            return false;
        }
    }

    /**
     * Checks if <code>exitCommand</code> is called for <code>Duke</code>
     * to terminate.
     * @return false.
     */
    public boolean isExit() {
        return false;
    }
}
