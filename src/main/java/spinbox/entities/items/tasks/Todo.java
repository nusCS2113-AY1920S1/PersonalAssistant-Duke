package spinbox.entities.items.tasks;

public class Todo extends NonSchedulable {
    public Todo(String taskName) {
        super(taskName);
        taskType = TaskType.TODO;
    }

    /**
     * This constructor is used for recreation of SpinBox.Tasks.Todo from storage.
     * @param done  1 if task has been marked complete, 0 otherwise.
     * @param taskName the name or description of the todo.
     */
    public Todo(int done, String taskName) {
        super(taskName);
        this.setDone(done == 1);
        taskType = TaskType.TODO;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String storeString() {
        return "T | " + super.storeString();
    }
}
