public class Todo extends Tasks {

    public Todo(String description, String type) {
        super(description, type);

    }

    public String toMessage() {
        return "[T]" + "[" + getStatusIcon() + "] " + description;
    }
}
