package duke.models.tasks;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a task.
 */
public class Task {
    private SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private SimpleStringProperty description;

    /**
     * Initialises the fields of a task.
     *
     * @param id          contains the id of a task which is an integer.
     * @param description contains the description of a task which is a string.
     */
    public Task(int id, String description) {
        this.id = new SimpleIntegerProperty(id);
        this.description = new SimpleStringProperty(description);
    }

    /**
     * Initialises the minimum fields required to setup a task.
     *
     * @param description contains the description of a task which is a string.
     */
    public Task(String description) {
        this.description = new SimpleStringProperty(description);
    }

    /**
     * It retrieves the id of the task.
     *
     * @return the id of the task which is an integer.
     */
    public int getId() {
        return this.id.get();
    }

    /**
     * It retrieves the description of the task.
     *
     * @return the description of the task.
     */
    public String getDescription() {
        return this.description.get();
    }

    /**
     * It sets the id of the task.
     *
     * @param id contains the id which is an integer.
     */
    public void setId(int id) {
        this.id.set(id);
    }

    /**
     * It sets the description of the task.
     *
     * @param description contains the description which is an string.
     */
    public void setDescription(String description) {
        this.description.set(description);
    }
}