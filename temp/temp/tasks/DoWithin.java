package duke.tasks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.exceptions.DukeException;
import duke.parser.DateDoWithin;

import java.util.Arrays;
import java.util.List;

public class DoWithin extends Task {
    protected String between;

    /**
     * This constructor instantiates the object for the DoWithin class.
     * @param description stores the DoWithin description.
     * @param between stores the time for the event in a particular format.
     * @throws DukeException when the user inputs event task in an invalid format.
     */
    public DoWithin(String description, String between)throws DukeException {
        super(description);
        this.between = between;
        try {
            List<String> splitDate = Arrays.asList(this.between.split(" to "));
            DateDoWithin from = new DateDoWithin(splitDate.get(0));
            from.dateTimer();
            DateDoWithin to = new DateDoWithin(splitDate.get(1));
            to.dateTimer();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(" Format for a DoWithin is: <DoWithin> /between <date> to <date>");
        }
    }

    @JsonCreator
    public DoWithin(@JsonProperty("do-within") String between) {
        this.between = between;
    }

    @Override
    public String toString() {
        return ("[W]" + super.toString() + " (between: "
                + between + ")");
    }

    @Override
    public String fileOutFormat() {
        return ("W" + super.fileOutFormat() + "|" + between);
    }

    @JsonGetter("do-within")
    public String getDoWithin() {
        return between;
    }



}

