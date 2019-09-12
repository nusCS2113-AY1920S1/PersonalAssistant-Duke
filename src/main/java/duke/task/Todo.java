package duke.task;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Todo. A Todo is a duke.task without time information.
 */
public class Todo extends Task {

    /**
     * Constructor for Todo.
     *
     * @param description the description of the Todo.
     */
    public Todo(@JsonProperty("description") String description) {
        super(description);
    }

    public String toString() {
        return String.format(super.toString(), "\uD83D\uDCCB");
    }
}
