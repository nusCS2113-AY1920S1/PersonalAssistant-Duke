import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a event. It is
 * extended from the <code>Task</code> class.
 */
public class Events extends Task {
    /**
     * A string that represents the time of the event.
     */
    protected String at;
    /**
     * Constructs a <code>Event</code> object. Date and time are parsed and
     * stored in <code>dateTime</code> field if input is of "dd/MM/yyyy HHmm"
     * format.
     * @param description A string that saves the description of the task.
     * @param at A string that specifies the time of the event.
     */
    public Events(String description, String at) {
        super(description);
        this.at = at;
    }
    /**
     * Returns a string pattern to the user output
     * @return A string which displays the type,
     *          description and deadline of the task.
     */
    @Override
    public String toString() {
        return "[E]" + super.printStatus() + " (at: " + super.timeFormatter(at) + ")";
    }
    /**
     * Returns a string with the following format to be read from a local file.
     * @return A string in a specific format to be read from a local file.
     */
    public String txtFormat() {
        return "E | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + super.timeFormatter(at);
    }
    /**
     * Returns a string with the following format to be stored in a local file
     * @return A string in a specific format to be stored in a local file.
     */
    public String writeTxt(){
        return "E | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + this.at;
    }
}