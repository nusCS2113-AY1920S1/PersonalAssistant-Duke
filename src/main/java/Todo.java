import java.util.List;

public class Todo extends Task {
    private String description;

    public Todo(List<String> input) {
        this.description = String.join(" ", input);
    }

    @Override
    public String toString() {
        return String.format("[T]%s %s", super.toString(), this.description);
    }
}
