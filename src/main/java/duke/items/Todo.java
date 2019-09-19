package duke.items;

/**
 * The least detailed task is the todo,
 * which consists of a deadline and done status.
 * It is effectively the (parent) Task class with a different print and save string.
 */

public class Todo extends Task {

    public Todo(int index, String description, int doAfter) {
        super(index, description, TaskType.TODO, doAfter); //Using the Task constructor. isDone is set to false.
    }

    @Override
    public String saveDetailsString() {
        return super.saveDetailsString();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}