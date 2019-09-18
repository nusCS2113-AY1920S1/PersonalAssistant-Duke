public class Todo extends Task {

    public Todo(String description) {
        super(description);
        this.type = "T";
    }

    public Todo(String description, boolean state) {
        super(description);
        this.isDone = state;
        this.type = "T";
    }

}