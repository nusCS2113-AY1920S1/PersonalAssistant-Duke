package duke.tasks;

import java.time.LocalDateTime;

public class DoWithin extends TaskWithDates {
    private LocalDateTime afterDate;
    /**
     * Initializes a task not yet done with the given description.
     *
     * @param description A description of this task.
     */
    public DoWithin(String description , LocalDateTime startDate , LocalDateTime afterDate) {
        super(description , startDate);
        this.afterDate = afterDate;
    }

    @Override
    public String toString(){
        return("[W]" + super.toString() + " within " + super.getStartDate() + " to " + this.afterDate);
    }
}
