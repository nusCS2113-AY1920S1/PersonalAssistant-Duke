package duke.tasks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.exceptions.DukeException;
import duke.parser.DateTimeRecognition;

import java.util.Arrays;
import java.util.List;

public class  Event extends Task {
    protected String at;

    /**
     * This constructor instantiates the object for the Event class.
     * @param description stores the event description.
     * @param at stores the time for the event in a particular format.
     * @throws DukeException when the user inputs event task in an invalid format.
     */
    public Event(String description, String at)throws DukeException {
        super(description);
        this.at = at;
        try {
            List<String> splitDate = Arrays.asList(at.split(" to "));
            DateTimeRecognition from = new DateTimeRecognition(splitDate.get(0));
            DateTimeRecognition to = new DateTimeRecognition(splitDate.get(1));
            from.dateTime();
            to.dateTime();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(" Format for an event is: <event> /at <data and time> to <date and time>");
        }

    }

    @JsonCreator
    public Event(@JsonProperty("at") String at) {
        this.at = at;
    }

    @Override
    public String toString() {
        return ("[E]" + super.toString() + " (at: "
                + at + ")");
    }

    @Override
    public String fileOutFormat() {
        return ("E" + super.fileOutFormat() + "|" + at);
    }

    @JsonGetter("at")
    public String getAt() {
        return this.at;
    }

}
