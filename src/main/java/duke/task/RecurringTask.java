package duke.task;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecurringTask extends Task {
    private int period;
   // private Deadline deadlineTask;
   // private Event eventTask;
    private Task task;

    public RecurringTask(@JsonProperty("task") Task task,
            @JsonProperty("period") int period) {
            super(task.getDescription());
            this.task = task;
            this.period = period;
    }
    //
    //public void setType(String type) {
    //    this.type = type;
    //}


    public Task getTask() {
        return task;
    }

    @Override
    public String toString() {
            return String.format("  %s  \n  %s   ", task.toString(), "/ " + period + " days");
    }
}
