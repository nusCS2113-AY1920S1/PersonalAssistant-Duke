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

public class UpcomingTaskReminder extends Reminder {

    private List<Task> tasks;

    public UpcomingTaskReminder(List<Task> tasks, TimeInterval remindBefore, TimeInterval checkEvery) throws ModTimeIntervalTooCloseException {
        super(remindBefore, checkEvery);
    }

    public UpcomingTaskReminder(List<Task> tasks, int minutesBefore, int minutesEvery) throws ModTimeIntervalTooCloseException {
        super(minutesBefore, minutesEvery);
    }

    public UpcomingTaskReminder(List<Task> tasks, TimeInterval remindBefore) throws ModTimeIntervalTooCloseException {
        super(remindBefore);
    }

    public UpcomingTaskReminder(List<Task> tasks, int minutesBefore) throws ModTimeIntervalTooCloseException {
        super(minutesBefore);
    }

    public UpcomingTaskReminder(List<Task> tasks) throws ModTimeIntervalTooCloseException {
        super();
    }

    @Override
    public void execute(LocalDateTime now) {
        try {
            new PlannerUi().printUpcomingTasks(
                    this.getUpcomingTasks(
                            new TimePeriodSpanning(now, now.plus(this.remindBefore))));
        } catch (ModInvalidTimePeriodException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Get upcoming tasks.
     * @param timePeriodSpanning How long before the task begin to remind
     * @return the upcoming tasks
     */
    private List<Task> getUpcomingTasks(TimePeriodSpanning timePeriodSpanning) {
        List<Task> upcomingTasks = new ArrayList<>();
        for (Task task: this.tasks) {
            if (task instanceof TaskWithPeriod
                    && !task.isDone()
                    && ((TaskWithPeriod)task).isClashing(timePeriodSpanning)) {
                upcomingTasks.add(task);
            }
        }
        return upcomingTasks;
    }
}
