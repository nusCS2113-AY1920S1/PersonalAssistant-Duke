package duke.util;

import duke.exceptions.ModInvalidTimePeriodException;
import duke.exceptions.ModTimeIntervalTooCloseException;
import duke.modules.DoWithin;
import duke.modules.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Reminder {
    private TimeInterval remindBefore;
    private TimeInterval checkEvery;
    private List<Task> tasks;
    private Thread thread;
    private static final TimeInterval minBefore = TimeInterval.ofMinutes(1);

    /**
     * Constructor for Reminder.
     * @param tasks TaskList object containing current active taskList.
     * @param remindBefore TimeInterval object indicating the amount of time to start reminding beforehand
     * @param checkEvery TimeInterval object indicating the amount of time to wait between reminds
     */
    public Reminder(List<Task> tasks, TimeInterval remindBefore, TimeInterval checkEvery)
            throws ModTimeIntervalTooCloseException {
        if (remindBefore.isLessThan(Reminder.minBefore)) {
            throw new ModTimeIntervalTooCloseException();
        }
        this.tasks = tasks;
        this.remindBefore = remindBefore;
        this.checkEvery = checkEvery;
        this.thread = new Thread(this::remind);
    }

    public Reminder(List<Task> tasks, TimeInterval remindBefore) throws ModTimeIntervalTooCloseException {
        this(tasks, remindBefore, TimeInterval.min(TimeInterval.ofHours(1), remindBefore));
    }

    public Reminder(List<Task> tasks, int minutes) throws ModTimeIntervalTooCloseException {
        this(tasks, TimeInterval.ofMinutes(minutes));
    }

    public Reminder(List<Task> tasks) throws ModTimeIntervalTooCloseException {
        this(tasks, TimeInterval.ofHours(6), TimeInterval.ofHours(1));
    }

    public void run() {
        this.thread.start();
    }

    /**
     * Force reminder to check upcoming tasks and remind immediately.
     */
    public void forceCheckReminder() {
        if (!this.thread.isAlive()) {
            this.thread.start();
        } else if (this.thread.getState().equals(Thread.State.TIMED_WAITING)) {
            this.thread.interrupt();
        }
    }

    private void remind() {
        LocalDateTime targetTime = LocalDateTime.now();
        LocalDateTime now;
        while (true) {
            now = LocalDateTime.now();
            if (now.isAfter(targetTime)) {
                targetTime = now.plus(this.checkEvery);
                try {
                    new Ui().printUpcomingTasks(
                            this.getUpcomingTasks(
                                    new TimePeriod(now, now.plus(this.remindBefore))));
                } catch (ModInvalidTimePeriodException e) {
                    System.out.println(e.getMessage());
                }
                long sleepSeconds = Math.max(TimeInterval.between(LocalDateTime.now(), targetTime)
                        .toDuration().getSeconds() - 1, 0);
                try {
                    Thread.sleep(sleepSeconds * 1000);
                } catch (InterruptedException ignored) {
                    targetTime = LocalDateTime.now();
                    break;
                }
            }
        }
    }
    
    private List<Task> getUpcomingTasks(TimePeriod timePeriod) {
        List<Task> upcomingTasks = new ArrayList<>();
        for (Task task: this.tasks) {
            /* TODO: Upon finishing implementing TimePeriod and getPeriod() for all task types,
                replace the if logic below with:
                    if (!task.isDone() && timePeriod.isClashing(task.getPeriod().getBegin())) {
             */
            if (task instanceof DoWithin
                    && !task.isDone()
                    && timePeriod.isClashing(((DoWithin) task).getPeriod().getBegin())) {
                upcomingTasks.add(task);
            }
        }
        return upcomingTasks;
    }
}
