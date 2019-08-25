import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Todo extends Task {
    private String description;

    public Todo(List<String> input) {
        this.description = String.join(" ", input);
    }

    public String toString() {
        return "[T]" + super.toString() + " " + this.description;
    }
}
