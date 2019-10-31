package planner.util.legacy.reminder;

import planner.logic.exceptions.legacy.ModInvalidTimePeriodException;
import planner.logic.exceptions.legacy.ModTimeIntervalTooCloseException;
import planner.logic.modules.legacy.task.Task;
import planner.logic.modules.legacy.task.TaskWithPeriod;
import planner.ui.cli.PlannerUi;
import planner.util.legacy.periods.TimeInterval;
import planner.util.legacy.periods.TimePeriodSpanning;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TwevleHourReminder extends Reminder {
    private List<Task> tasks;

    public TwevleHourReminder(List<Task> tasks, TimeInterval remindBefore, TimeInterval checkEvery)
            throws ModTimeIntervalTooCloseException {
        super(remindBefore, checkEvery);
    }

    public TwevleHourReminder(List<Task> tasks, int minutesBefore, int minutesEvery)
            throws ModTimeIntervalTooCloseException {
        super(minutesBefore, minutesEvery);
    }

    public TwevleHourReminder(List<Task> tasks, TimeInterval remindBefore) throws ModTimeIntervalTooCloseException {
        super(remindBefore);
    }

    public TwevleHourReminder(List<Task> tasks, int minutesBefore) throws ModTimeIntervalTooCloseException {
        super(minutesBefore);
    }

    public TwevleHourReminder(List<Task> tasks) throws ModTimeIntervalTooCloseException {
        super();
    }

    @Override
    public void execute(LocalDateTime now) {
        try {
            now = LocalDateTime.now();
            this.thread.sleep(43200000);
            this.thread.notify();
            new PlannerUi().reminderMsg();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
