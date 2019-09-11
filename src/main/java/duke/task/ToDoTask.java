package duke.task;

public class ToDoTask extends Task {

    public ToDoTask(String _name) {
        super(_name);
        type = 'T';
    }

    @Override
    public String toString() {
        return "[" + type + "]" + super.toString();
    }
}
