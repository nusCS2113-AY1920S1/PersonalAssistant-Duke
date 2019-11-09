package seedu.hustler.logic.command.timer;

import seedu.hustler.logic.command.Command;
import seedu.hustler.ui.timer.TimerManager;
import seedu.hustler.schedule.Scheduler;
import seedu.hustler.schedule.Scheduler;

/**
 * Command that stops the timer, updates time spent on
 * tasks and also displays current schedule.
 */
public class StopTimerCommand extends Command {
    /**
     * Stops the timer and updates time spent on
     * each task based on the schedule of work.
     */
    public void execute() {
        TimerManager.stopTimer();
        Scheduler.confirm();
    }
}
