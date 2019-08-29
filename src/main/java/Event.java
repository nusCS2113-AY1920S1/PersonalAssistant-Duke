import java.util.Arrays;
import java.util.List;

public class Event extends Task {
    protected String at;
    private DateTimeRecognition to;
    private DateTimeRecognition from;

    public Event(String description, String at)throws DukeException {
        super(description);
        this.at = at;
        try {
            List<String> splitDate = Arrays.asList(at.split(" to "));
            from = new DateTimeRecognition(splitDate.get(0));
            to = new DateTimeRecognition(splitDate.get(1));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException(" Format for an event is: <event> /at <data and time> to <date and time>");
        }
        from.dateTime();
        to.dateTime();
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
}