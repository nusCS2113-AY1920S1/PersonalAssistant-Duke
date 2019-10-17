package duke.tasks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DoAfter extends Task {
    protected String after;

    public DoAfter(String description, String after) {
        super(description);
        this.after = after;
    }

    @JsonCreator
    public DoAfter(@JsonProperty("do-after") String after) {
        this.after = after;
    }

    @Override
    public String toString() {
        return ("[A]" + super.toString() + " (after " + this.after + ")");
    }

    @Override
    public String fileOutFormat() {
        return ("A" + super.fileOutFormat() + "|" + this.after);
    }


    @JsonGetter("do-after")
    public String getDoAfter() {
        return after;
    }
}