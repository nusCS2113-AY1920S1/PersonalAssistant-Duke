package duke.tasks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.FileOutputStream;

public class FixedDuration extends Task {
    protected String needs;

    public FixedDuration(String description, String needs) {
        super(description);
        this.needs = needs;
    }

    @JsonCreator
    public FixedDuration(@JsonProperty("needs") String needs) {
        this.needs = needs;
    }

    @Override
    public String toString() {
        return ("[F]" + super.toString() + " (needs " + this.needs + ")");
    }

    @Override
    public String fileOutFormat() {
        return ("F" + super.fileOutFormat() + "|" + this.needs);
    }

    @JsonGetter("needs")
    public String getNeeds() {
        return needs;
    }
}
