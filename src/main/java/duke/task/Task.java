package duke.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Represents a duke.task.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Deadline.class),
        @JsonSubTypes.Type(value = Todo.class),
        @JsonSubTypes.Type(value = Event.class),
        @JsonSubTypes.Type(value = FixedDurationTask.class),
        @JsonSubTypes.Type(value = RecurringTask.class)
}
)
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public abstract class Task {

    protected String description;
    protected boolean done;

    /**
     * Constructor for Task.
     *
     * @param description the description of the Task.
     */
    public Task(String description) {
        this.description = description;
        this.done = false;
    }

    /**
     * Returns the description of the duke.task.
     * @return the description of the duke.task.
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    /**
     * Set the status of the duke.task.
     *
     * @param done the status of the duke.task.
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * Returns an icon indicating if the duke.task is done.
     * @return "✅" if the duke.task is done; otherwise "❌"
     */
    @JsonIgnore
    public String getStatusIcon() {
        return (done ? "✅" : "❌"); //return tick or X symbols
    }

    /**
     * Returns a summary of the duke.task.
     * @return Returns a summary of the duke.task, including description and the status icon.
     */
    @Override
    public String toString() {
        return "%1$s " + getStatusIcon() + " " + description;
    }
}
