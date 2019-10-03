package eggventory.items;

import eggventory.enums.TaskType;

/**
 * The least detailed task is the todo,
 * which consists of a deadline and done status.
 * It is effectively the (parent) Stock class with a different print and save string.
 */

public class Todo extends Task {

    private int duration = 0;

    public Todo(String description) {
        super(description, TaskType.TODO); //Using the Stock constructor. isDone is set to false.
    }

    public Todo(String description, int duration) {
        super(description, TaskType.TODO);
        this.duration = duration;
    }

    @Override
    public String saveDetailsString() {
        return super.saveDetailsString();
    }

    @Override
    public String toString() {
        String result;
        if (duration > 0) {
            result = super.toString() + " (needs " + duration + " hours)";
        } else {
            result = super.toString();
        }
        return result;
    }
}