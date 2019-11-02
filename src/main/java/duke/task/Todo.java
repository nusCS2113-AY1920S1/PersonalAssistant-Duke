package duke.task;

import duke.exception.DukeException;
import duke.parser.Convert;

import java.util.Date;

/**
 * This class...
 */
public class Todo extends Task {

    private Date date;

    /**
     * The constructor method for this class.
     */
    public Todo(String description) {
        super(description);
        date = null;
    }

    @Override
    public void setNewDate(String date) throws DukeException {
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