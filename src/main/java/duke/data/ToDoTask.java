package duke.data;

public class ToDoTask extends Task {
    public ToDoTask(String name) {
        super(name);
        type = 'T';
    }

    public ToDoTask(String name, Reminder reminder) {
        super(name, reminder);
        type = 'T';
    }

    @Override
    public String toString() {
        return "[" + type + "]" + super.toString();
    }
}
