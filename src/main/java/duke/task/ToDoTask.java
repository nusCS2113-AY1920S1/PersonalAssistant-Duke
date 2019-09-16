package duke.task;

public class ToDoTask extends Task {
    public ToDoTask(String name) {
        super(name);
        type = 'T';
    }

    @Override
    public String toString() {
        return "[" + type + "]" + super.toString();
    }
}
