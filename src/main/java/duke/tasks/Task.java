package duke.tasks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Task {
    public String description;
    public boolean isDone;
    private final String ticks = "Y";
    private final String cross = "N";

    @JsonCreator
    public Task(@JsonProperty("description") String description) {
        this.description = description;
        this.isDone = false;
    }

    Task() {

    }

    public String getStatusIcon() {
        return (isDone ? ticks : cross);
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public boolean checkKeyword(String str) {
        return (this.description.contains(str));
    }

    public String toString() {
        return ("[" + getStatusIcon() + "] "
                + description);
    }

    public String fileOutFormat() {
        return ("|" + isDone + "|" + description);
    }

    @JsonGetter("description")
    public String getDescription() {
        return description;
    }

    @JsonGetter("is Done")
    public boolean getIsDone() {
        return isDone;
    }

    @JsonSetter("is Done")
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }
}