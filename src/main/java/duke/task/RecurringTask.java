package duke.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class RecurringTask {

    public enum RecurringFrequency { DAILY, WEEKLY, MONTHLY};
    public enum TaskType { TODO, DEADLINE, EVENT}

    private LocalDateTime lastRecordedTime;
    private RecurringFrequency frequency;
    private TaskType taskType;

    public RecurringTask(Task task, RecurringFrequency frequency) {
        if (task instanceof Todo) { this.taskType = TaskType.TODO; }
        if (task instanceof Deadline) { this.taskType = TaskType.DEADLINE; }
        if (task instanceof Event) { this.taskType = TaskType.EVENT; }

        if (task.getDateTime() != null) {
            lastRecordedTime = task.getDateTime();
        }
        this.frequency = frequency;
    }

    public RecurringFrequency getFrequency() { return frequency; }

    /**
     * When a task is recurring, method compares current time to listed date.
     * If the task's date is outdated, then it will update to be for the next day.
     */
    public LocalDateTime recurringTaskTimeUpdate(Task task) {
        if (lastRecordedTime != null) {
            try {
                LocalDateTime currentTime = LocalDateTime.now();
                if (lastRecordedTime.isBefore(currentTime)) {

                    switch (this.frequency) {
                        case DAILY:
                            while (lastRecordedTime.isBefore(currentTime) || task.isDone()) {
                                lastRecordedTime = lastRecordedTime.plusDays(1);
                                if (task.isDone()) { task.isDone = false; }
                            }
                        case WEEKLY:
                            while (lastRecordedTime.isBefore(currentTime) || task.isDone()) {
                                lastRecordedTime = lastRecordedTime.plusWeeks(1);
                                if (task.isDone) { task.isDone = false; }
                            }
                        case MONTHLY:
                            while (lastRecordedTime.isBefore(currentTime) || task.isDone()) {
                                lastRecordedTime = lastRecordedTime.plusMonths(1);
                                if (task.isDone) { task.isDone = false; }
                            }
                    }
                }
            } catch (DateTimeParseException e) {
                System.out.println("I couldn't update your recurring events' times.");
            }
        }
        return lastRecordedTime;
    }
}
