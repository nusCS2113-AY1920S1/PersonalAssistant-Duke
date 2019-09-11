import java.time.LocalDateTime;

/**
 * Deadline is a type of Task that stores the task description and due date of said task.
 */
public class Deadline extends Task {

    protected LocalDateTime by;

    /**
     * Creates an instance with specified task description and due date.
     * @param description String of what the task entails.
     * @param date Time data of the day the task is due.
     */
    public Deadline(String description, LocalDateTime date) {
        super(description);
        type = 'D';
        this.by = date;
    }

    /**
     * Returns a string of when the task is due in the format '(by 11/11/1111 0000)'
     * for printing purposes.
     * @return string of when the task is due in the format '(by 11/11/1111 0000)'.
     */
    @Override
    public String getDate () {
        String byStr = timeToString(by);
        return "(by: " + byStr + ")";
    }

    /**
     * Returns a string of when the task is due in the format ' | 11/11/1111 0000'
     * for saving purposes.
     * @return a string of when the task is due in the format ' | 11/11/1111 0000'
     */
    @Override
    public String formatDateSave() {
        String byStr = timeToString(by);
        return " | " + byStr;
    }
}
