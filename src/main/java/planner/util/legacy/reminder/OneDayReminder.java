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

public class OneDayReminder extends Reminder {
    private List<Task> tasks;

    public OneDayReminder(List<Task> tasks, TimeInterval remindBefore, TimeInterval checkEvery)
            throws ModTimeIntervalTooCloseException {
        super(remindBefore, checkEvery);
    }

    public OneDayReminder(List<Task> tasks, int minutesBefore, int minutesEvery) throws
            ModTimeIntervalTooCloseException {
        super(minutesBefore, minutesEvery);
    }

    public OneDayReminder(List<Task> tasks, TimeInterval remindBefore) throws ModTimeIntervalTooCloseException {
        super(remindBefore);
    }

    public OneDayReminder(List<Task> tasks, int minutesBefore) throws ModTimeIntervalTooCloseException {
        super(minutesBefore);
    }

    public OneDayReminder(List<Task> tasks) throws ModTimeIntervalTooCloseException {
        super();
    }

    @Override
    public void execute(LocalDateTime now) {
        try {
            now = LocalDateTime.now();
            Thread.sleep(86400000);
            this.thread.notify();
            new PlannerUi().reminderMsg();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
