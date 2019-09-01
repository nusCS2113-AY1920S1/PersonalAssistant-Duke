import java.util.List;

public class Todo extends Task {
    private String description;

    public Todo(List<String> input) throws DukeException {
        this.description = String.join(" ", input);
        if (input.size() == 0) {
            throw new DukeException("Format for todo: todo <task>");
        }
    }

    @Override
    public boolean containsKeyword(String keyword) {
        return this.description.contains(keyword);
    }

    @Override
    public String toString() {
        return String.format("[T]%s %s", super.toString(), this.description);
    }
}
