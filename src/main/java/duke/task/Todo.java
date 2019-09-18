package duke.task;

import duke.parser.Parser;

import java.util.Date;

/**
 * Represents a specific {@link Task} todo, not necessarily indicating a deadline or a specific date
 */
public class Todo extends Task {

    private Date date;
    public Todo(String description) {
        super(description);
        date=null;
    }

    @Override
    public void setNewDate(String date) {
        this.date= Parser.getDate(date);
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
        return this.isDone() ? "T|1|" + this.getDescription() : "T|0|" + this.getDescription();
    }
}