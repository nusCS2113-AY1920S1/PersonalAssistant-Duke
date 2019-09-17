package duke.task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class RecurringTask extends Task {

    public enum RecurringFrequency { DAILY, WEEKLY, MONTHLY};
    public enum TaskType { TODO, DEADLINE, EVENT}

    private Task task;
    private RecurringFrequency frequency;
    private TaskType taskType;

    public RecurringTask(Task task, RecurringFrequency frequency) {
        super(task.description);
        this.task = task;
        if (task instanceof Todo) { this.taskType = TaskType.TODO; }
        if (task instanceof Deadline) { this.taskType = TaskType.DEADLINE; }
        if (task instanceof Event) { this.taskType = TaskType.EVENT; }

        this.frequency = frequency;
    }

    public Task getTask() { return task; }

    public RecurringFrequency getFrequency() { return frequency; }

    /**
     * When a task is recurring, method compares current time to listed date.
     * If the task's date is outdated, then it will update to be for the next day.
     */
    public void recurringTaskTimeUpdate() {
        if (task.getDateTime() != null) {
            try {
                LocalDateTime currentTime = LocalDateTime.now();
                if (task.getDateTime().isBefore(currentTime)) {

                    switch (this.frequency) {
                        case DAILY:
                            Duration dayDifference = Duration.between(currentTime, task.getDateTime());
                            int totalDaysDifference = (int) Math.abs(dayDifference.toDays());
                            if (totalDaysDifference > 0 ) {
                                task.updateLocalDateTime(task.getDateTime().plusDays(totalDaysDifference).toString());
                                if (task.isDone) { task.isDone = false; }
                            }
                        case WEEKLY:
                            Duration weekDifference = Duration.between(currentTime, task.getDateTime());
                            int totalWeeksDifference = (int) Math.abs(weekDifference.toDays()/7);
                            if (totalWeeksDifference > 0) {
                                task.updateLocalDateTime(task.getDateTime().plusWeeks(totalWeeksDifference).toString());
                            }
                            if (task.isDone) { task.isDone = false; }
                        case MONTHLY:
                            Duration monthDifference = Duration.between(currentTime, task.getDateTime());
                            long totalMonthsDifference = (int) Math.abs(monthDifference.toDays()/30);
                            if (totalMonthsDifference > 0) {
                                task.updateLocalDateTime(task.getDateTime().plusMonths(totalMonthsDifference).toString());
                            }
                            if (task.isDone) { task.isDone = false; }
                    }
                }
            } catch (DateTimeParseException e) {
                System.out.println("I couldn't update your recurring events' times.");
            }
        }
    }

    public LocalDateTime getUpdatedTime() {
        return ld;
    }

    @Override
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
