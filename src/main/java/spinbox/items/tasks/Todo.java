package spinbox.items.tasks;

public class Todo extends Task {
    public Todo(String taskName) {
        super(taskName);
    }

    public Todo(int done, String taskName) {
        super(taskName);
        this.setDone(done == 1);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String storeString() {
        return "T | " + super.storeString();
    }

    @Override
    String getStartDateString() {
        return null;
    }

    @Override
    String getEndDateString() {
        return null;
    }
}
