package task;

import parser.DateTimeExtractor;

import java.time.LocalDateTime;

public class TodoWithinPeriod extends Todo {

    /**
     * Creates a ToDo task with a specific duration and timing.
     *
     * @param description description of task
     * @param startDate   start time of the task
     * @param endDate     end time of the task
     */
    public TodoWithinPeriod(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        String message = super.getPriorityIcon() + "[T]" + "[" + super.getStatusIcon() + "] " + this.description;
        String dateString = " (from: " + this.startDate.format(DateTimeExtractor.DATE_FORMATTER) + ")" + " (to: "
            + this.endDate.format(DateTimeExtractor.DATE_FORMATTER) + ")";
        return message.concat(dateString);
    }
}
