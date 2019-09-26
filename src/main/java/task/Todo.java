package task;

import java.io.FileOutputStream;
import java.io.IOException;

public class Todo extends Task {

    /**
     * task.Todo Constructor
     * @param description task description
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Constructor from text file.
     * @param i isDone status
     * @param description task description
     */
    public Todo(String i, String description, String snooze) {
        super(description);
        this.isDone = i.equals("1");
        this.isSnooze = snooze.equals("1");
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns a string that is formatted for the text file.
     * @return String
     */
    @Override
    public String toWriteFile() {
        int boolToInt = this.isDone ? 1 : 0;
        int snoozebooltoInt = this.isSnooze ? 1 : 0;
        return "T | " + boolToInt + " | " + this.description + " | " + snoozebooltoInt + "\n";
    }
}
