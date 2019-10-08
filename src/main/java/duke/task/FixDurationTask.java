package duke.task;

import duke.command.*;
import duke.core.*;
import duke.exception.*;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * Represents a Todo task. Stores a description and whether it is done.
 */
public class FixDurationTask extends Task {
    /**
     * Constructor for a new Todo. Called when a Todo is first created.
     * @param description a String description of the Todo.
     */
    public FixDurationTask(String description, String duration) throws ParseException {
        super(description);
        this.date = stringToDate(duration);
        this.type = 'F';
    }

    /**
     * Constructor for an existing Todo from our saved tasks. Called to
     * create saved tasks.
     * @param description a String description of the Todo.
     * @param isDone a Boolean indicating whether the task has been fulfilled.
     */
    public FixDurationTask(String description, String duration, boolean isDone) throws ParseException  {
        super(description, isDone);
        this.date = stringToDate(duration);
        this.type = 'F';
    }

    @Override
    public String toString() {
        return super.toString() + " (needs: " + getDate() + ")";
    }
}