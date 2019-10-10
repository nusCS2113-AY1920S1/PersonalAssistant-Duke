package duke.tasks;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Todo extends Task {

    @JsonCreator
    public Todo(@JsonProperty("description") String description) {
        super(description);
    }

    @Override
    public String toString() {
        return ("[T]" + super.toString());
    }

    @Override
    public String fileOutFormat() {
        return ("T" + super.fileOutFormat());
    }



}