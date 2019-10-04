package duke.tasks;

import duke.exceptions.DukeException;
import duke.parser.DateTimeRecognition;

public class Deadline extends Task {
    protected String by;
    private DateTimeRecognition convertDate;

    /**
     * Instantiates the Deadline class.
     * @param description stores the deadline task added by the user.
     * @param by stores the time by which the user is expected to finish the task.
     * @throws DukeException when the user inputs a deadline task in invalid format.
     */
    public Deadline(String description, String by)throws DukeException {
        super(description);
        this.by = by;
        convertDate = new DateTimeRecognition(this.by);
        convertDate.dateTime();
    }

    @Override
    public String toString() {
        return ("[D]" + super.toString() + " (by: "
                + by + ")");
    }

    @Override
    public String fileOutFormat() {
        return ("D" + super.fileOutFormat() + "|" + by);
    }
}