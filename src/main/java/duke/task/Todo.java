package duke.task;

import duke.parser.Convert;

import java.util.Date;

/**
 * Represents a specific {@link Task} todo, not necessarily indicating a deadline or a specific date.
 */
public class Todo extends Task {

    private Date date;

    /**
     * The constructor method for Todo.
     */
    public Todo(String description) {
        super(description);
        date = null;
    }

    @Override
    public void setNewDate(String date) {
        this.date = Convert.stringToDate(date);
    }

    @Override
    public Date getCurrentDate() {
        return date;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String printInFile() {
        if (this.isDone()) {
            return "T|1|" + this.getDescription();
        } else {
            return "T|0|" + this.getDescription();
        }
    }
}