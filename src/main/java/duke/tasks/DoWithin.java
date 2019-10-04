package duke.tasks;

import duke.exceptions.DukeException;
import duke.parser.DateDoWithin;

import java.util.Arrays;
import java.util.List;

public class DoWithin extends Task {
    protected String between;
    private DateDoWithin to;
    private DateDoWithin from;

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
            List<String> splitDate = Arrays.asList(between.split(" to "));
            from = new DateDoWithin(splitDate.get(0));
            to = new DateDoWithin(splitDate.get(1));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(" Format for an DoWithin is: <DoWithin> /between <date> to <date>");
        }
        from.dateTime();
        to.dateTime();
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
}