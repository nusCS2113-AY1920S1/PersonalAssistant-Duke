package duke.models.tasks;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a task.  Task is an abstract class that can not be
 * instantiated
 */
public class Task {
    private SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private SimpleStringProperty description;

    /**
     * .
     *
     * @param id          .
     * @param description .
     */
    public Task(int id, String description) {
        this.id = new SimpleIntegerProperty(id);
        this.description = new SimpleStringProperty(description);
    }

    /**
     * .
     *
     * @param description .
     */
    public Task(String description) {
        this.description = new SimpleStringProperty(description);
    }

    /**
     * Returns a string with the status icon and the description of the task.
     *
     * @return A string in a specific format with the status and description of the task.
     */
    public String printDescription() {
        return " " + description + " ";
    }

    /**
     * Returns the description of the task.
     *
     * @return A string that represents the specific activity associated with the task.
     */
    public int getId() {
        return this.id.get();
    }

    /**
     * .
     *
     * @return .
     */
    public String getDescription() {
        return this.description.get();
    }

    /**
     * .
     *
     * @param id .
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * .
     *
     * @param description .
     */
    public void setDescription(String description) {
        this.description.set(description);
    }
}