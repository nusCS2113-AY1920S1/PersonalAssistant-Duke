import java.time.LocalDateTime;

/**
 * Deadline is a type of Task that stores the task description and due date of said task.
 */
public class Event extends Task {

    protected LocalDateTime at;

    /**
     * Creates an instance with specified task description and due date.
     * @param description String of what the task entails.
     * @param date Time data of the day the task is due.
     */
    public Event(String description, LocalDateTime date) {
        super(description);
        type = 'E';
        this.at = date;
    }

    /**
     * Returns a string of when the task is due in the format '(at 11/11/1111 0000)'
     * for printing purposes.
     * @return string of when the task is due in the format '(at 11/11/1111 0000)'.
     */
    @Override
    public String getDate() {
        String atStr = timeToString(at);
        return "(at: " + atStr + ")";
    }

    /**
     * Returns a string of when the task is due in the format ' | 11/11/1111 0000'
     * for saving purposes.
     * @return a string of when the task is due in the format ' | 11/11/1111 0000'
     */
    @Override
    public String formatDateSave() {
        String atStr = timeToString(at);
        return " | " + atStr;
    }
}
