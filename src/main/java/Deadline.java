import java.util.List;

public class Deadline extends Task {
    private String description;
    private String deadline;

    public Deadline(List<String> input) {
        int separatorIndex = input.indexOf("/by");
        this.description = String.join(" ", input.subList(0, separatorIndex));
        this.deadline = String.join(" ", input.subList(separatorIndex + 1, input.size()));
    }

    @Override
    public String toString() {
        return String.format("[D]%s %s (by: %s)", super.toString(), this.description, this.deadline);
    }
}
