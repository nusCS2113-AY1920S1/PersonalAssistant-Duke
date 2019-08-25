import java.util.List;

public class Deadline extends Task {
    private String description;
    private DateTime deadline;

    public Deadline(List<String> input) throws DukeException {
        int separatorIndex = input.indexOf("/by");
        if (input.size() == 0 || separatorIndex <= 0) {
            throw new DukeException("Format for deadline: deadline <task> /by <deadline>");
        }
        this.description = String.join(" ", input.subList(0, separatorIndex));
        this.deadline = new DateTime(input.subList(separatorIndex + 1, input.size()));
    }

    @Override
    public String toString() {
        return String.format("[D]%s %s (by: %s)", super.toString(), this.description, this.deadline);
    }
}
