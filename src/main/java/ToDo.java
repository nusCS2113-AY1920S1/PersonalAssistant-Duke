public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String dataString() {
        return "T | " + (this.isDone ? 1 : 0) + " | " + this.description;
    }
}
