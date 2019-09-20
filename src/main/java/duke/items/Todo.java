package duke.items;

/**
 * The least detailed task is the todo,
 * which consists of a deadline and done status.
 * It is effectively the (parent) Task class with a different print and save string.
 */

public class Todo extends Task {

    private int duration = 0;

    public Todo(String description, String doAfter) {
        super(description, TaskType.TODO, doAfter); //Using the Task constructor. isDone is set to false.
    }

    public Todo(String description, String doAfter, int duration) {
        super( description, TaskType.TODO, doAfter);
        this.duration = duration;
    }

    @Override
    public String saveDetailsString() {
        return super.saveDetailsString();
    }

    @Override
    public String toString() {
        if (duration > 0) {
            return  super.toString() + " (needs " + duration + " hours)";
        }
        return super.toString();
    }
}