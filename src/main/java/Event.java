import java.util.List;

public class Event extends Task {
    private String description;
    private DateTime start, end;

    public Event(List<String> input) throws DukeException {
        int separatorIndex = input.indexOf("/at");
        if (input.size() == 0 || separatorIndex <= 0) {
            throw new DukeException("Format for event: event <event> /at <start datetime> to <end> datetime");
        }
        try {
            this.description = String.join(" ", input.subList(0, separatorIndex));
            this.start = new DateTime(input.subList(separatorIndex + 1, separatorIndex + 3));
            this.end = new DateTime(input.subList(separatorIndex + 4, separatorIndex + 6));
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Format for event: event <event> /at <start datetime> to <end datetime>");
        }
    }

    @Override
    public String toString() {
        return String.format("[E]%s %s (at: %s to %s)", super.toString(), this.description, this.start, this.end);
    }
}
