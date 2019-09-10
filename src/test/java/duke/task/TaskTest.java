package duke.task;

/**
 * This class is meant to override the Task class for ease of testing individual Task functions.
 */
public class TaskTest extends Task {
    public TaskTest() {
        super("");
    }

    /**
     * This overrides the toString for ease of testing
     * @return task done or task not done
     */
    @Override
    public String toString() {
        return done ? "TaskTest: task done" : "TaskTest: task not done";
    }

    /**
     * This overrides the export function for ease of testing.
     * @return export done or not done
     */
    @Override
    public String export() {
        return done ? "TaskTest: export done" : "TaskTest: export not done";
    }
}
