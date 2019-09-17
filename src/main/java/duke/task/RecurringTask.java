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
                            Duration dayDifference = Duration.between(currentTime, lastRecordedTime);
                            int totalDaysDifference = (int) Math.abs(dayDifference.toDays());
                            if ((totalDaysDifference > 0 ) || task.isDone()) {
                                lastRecordedTime = lastRecordedTime.plusDays(totalDaysDifference);
                                if (task.isDone) { task.isDone = false; }
                            }
                        case WEEKLY:
                            Duration weekDifference = Duration.between(currentTime, lastRecordedTime);
                            int totalWeeksDifference = (int) Math.abs(weekDifference.toDays()/7);
                            if ((totalWeeksDifference > 0) || task.isDone()) {
                                lastRecordedTime = lastRecordedTime.plusWeeks(totalWeeksDifference);
                                if (task.isDone) { task.isDone = false; }
                            }
                        case MONTHLY:
                            Duration monthDifference = Duration.between(currentTime, lastRecordedTime);
                            long totalMonthsDifference = (int) Math.abs(monthDifference.toDays()/30);
                            if ((totalMonthsDifference > 0) || task.isDone()) {
                                lastRecordedTime = lastRecordedTime.plusMonths(totalMonthsDifference);
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

    public String writeTxt() {
        switch (frequency) {
            case DAILY:
                return "DAILY";
            case WEEKLY:
                return "WEEKLY";
            case MONTHLY:
                return "MONTHLY";
        }
        return "ONCE";
    }
}
