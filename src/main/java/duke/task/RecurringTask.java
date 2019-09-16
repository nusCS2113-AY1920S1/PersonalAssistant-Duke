package duke.task;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecurringTask extends Task {
    public int period;
    public String type;

    public RecurringTask(@JsonProperty("description") String description,
            @JsonProperty("period") int period) {
            super(description);
            this.period = period;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "  " + period + " days  ";
    }
}
