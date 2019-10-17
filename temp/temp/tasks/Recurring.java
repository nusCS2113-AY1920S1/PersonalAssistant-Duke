package duke.tasks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Recurring extends Task {
    protected String forTask;

    public Recurring(String description, String forTask) {
        super(description);
        this.forTask = forTask;
    }

    @JsonCreator
    public Recurring(@JsonProperty("for") String forTask) {
        this.forTask = forTask;
    }

    @Override
    public String toString() {
        return ("[R]" + super.toString() + " (after " + this.forTask + ")");
    }

    @Override
    public String fileOutFormat() {
        return ("R" + super.fileOutFormat() + "|" + this.forTask);
    }

    @JsonGetter("for")
    public String getForTask() {
        return forTask;
    }
}

