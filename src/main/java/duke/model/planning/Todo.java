package duke.model.planning;

import duke.model.events.Task;

/**
 * Class representing a to-do.
 */
public class Todo extends Task {
    /**
     * Initializes a to-do not yet done with the given description.
     *
     * @param description A description of this to-do.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of this to-do.
     *
     * @return The desired string representation.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

