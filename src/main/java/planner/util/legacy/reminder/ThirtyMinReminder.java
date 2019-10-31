package planner.util.legacy.reminder;

import planner.logic.command.ReminderCommand;
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

public class ThirtyMinReminder extends Reminder {
    private List<Task> tasks;

    public ThirtyMinReminder(List<Task> tasks, TimeInterval remindBefore, TimeInterval checkEvery)
            throws ModTimeIntervalTooCloseException {
        super(remindBefore, checkEvery);
    }

    public ThirtyMinReminder(List<Task> tasks, int minutesBefore, int minutesEvery)
            throws ModTimeIntervalTooCloseException {
        super(minutesBefore, minutesEvery);
    }

    public ThirtyMinReminder(List<Task> tasks, TimeInterval remindBefore) throws ModTimeIntervalTooCloseException {
        super(remindBefore);
    }

    public ThirtyMinReminder(List<Task> tasks, int minutesBefore) throws ModTimeIntervalTooCloseException {
        super(minutesBefore);
    }

    public ThirtyMinReminder(List<Task> tasks) throws ModTimeIntervalTooCloseException {
        super();
    }

    @Override
    public void execute(LocalDateTime now) {
        try {
            now = LocalDateTime.now();
            //Thread.sleep(1800000);
            Thread.sleep(2000);
            this.thread.notify();
            new PlannerUi().reminderMsg();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
