package duke.tasks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.exceptions.DukeException;
import duke.parser.DateTimeRecognition;

import java.time.LocalDateTime;

public class Deadline extends Task {
    protected String by;

    /**
     * Instantiates the Deadline class.
     * @param description stores the deadline task added by the user.
     * @param by stores the time by which the user is expected to finish the task.
     * @throws DukeException when the user inputs a deadline task in invalid format.
     */
    public Deadline(String description, String by)throws DukeException {
        super(description);
        this.by = by;
        DateTimeRecognition convertDate = new DateTimeRecognition(this.by);
        convertDate.dateTime();
    }

    @JsonCreator
    public Deadline(@JsonProperty("deadline") String by) {
        this.by = by;
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


    @JsonGetter("deadline")
    public String getDeadline() {
        return by;
    }
}