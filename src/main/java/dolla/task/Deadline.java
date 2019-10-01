package dolla.task;

import java.time.LocalDateTime;

/**
 * duke.task.Deadline is a type of duke.task.Task that stores the task description and due date of said task.
 */
public class Deadline extends Task {

    /**
     * Creates an instance with specified task description and due date.
     * @param description String of what the task entails.
     * @param date duke.Time data of the day the task is due.
     */
    public Deadline(String description, LocalDateTime date) {
        super(description);
        type = 'D';
        this.date = date;
    }

    /**
     * Returns a string of when the task is due in the format '(by 11/11/1111 0000)'
     * for printing purposes.
     * @return string of when the task is due in the format '(by 11/11/1111 0000)'.
     */
    @Override
    public String getDateStr() {
        String byStr = timeToString(date);
        return "(by: " + byStr + ")";
    }

    /**
     * Returns a string of when the task is due in the format ' | 11/11/1111 0000'
     * for saving purposes.
     * @return a string of when the task is due in the format ' | 11/11/1111 0000'
     */
    @Override
    public String formatDateSave() {
        String byStr = timeToString(date);
        return " | " + byStr;
    }
}
