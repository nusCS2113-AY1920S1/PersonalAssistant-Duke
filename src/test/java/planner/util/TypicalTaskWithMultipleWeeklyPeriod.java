package planner.util;

import planner.logic.modules.legacy.task.TaskWithMultipleWeeklyPeriod;
import planner.util.legacy.periods.TimeInterval;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class TypicalTaskWithMultipleWeeklyPeriod {

    private static final String DEFAULT_VALID_TASK = "task";
    private static final LocalTime DEFAULT_BEGIN = LocalTime.of(18,00);
    private static final LocalTime DEFAULT_END = LocalTime.of(20,00);
    private static final DayOfWeek DEFAULT_DAY_OF_WEEK = DayOfWeek.MONDAY;
    private static final TimeInterval DURATION = new TimeInterval();

    private String task;
    private LocalTime begin;
    private LocalTime end;
    private DayOfWeek dayOfWeek;
    private TimeInterval duration;

    public static final TaskWithMultipleWeeklyPeriod TYPICAL_TASK_WITH_MULTIPLE_WEEKLY_PERIOD =
            new TaskWithMultipleWeeklyPeriod(DEFAULT_VALID_TASK,DEFAULT_BEGIN,DEFAULT_END,DEFAULT_DAY_OF_WEEK);

    public TypicalTaskWithMultipleWeeklyPeriod withTask(String task) {
        this.task = task;
        return this;
    }

    public TaskWithMultipleWeeklyPeriod build() {
        return new TaskWithMultipleWeeklyPeriod(task, begin, end, dayOfWeek);
    }

}
