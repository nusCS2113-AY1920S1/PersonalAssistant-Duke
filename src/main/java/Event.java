import java.util.List;

public class Event extends Task {
    private String description;
    private String time;

    public Event(List<String> input) throws DukeException {
        int separatorIndex = input.indexOf("/at");
        if (input.size() == 0 || separatorIndex <= 0) {
            throw new DukeException("Format for event: event <event> /at <deadline>");
        }
        this.description = String.join(" ", input.subList(0, separatorIndex));
        this.time = String.join(" ", input.subList(separatorIndex + 1, input.size()));
    }

    @Override
    public String toString() {
        return String.format("[E]%s %s (at: %s)", super.toString(), this.description, this.time);
    }
}
