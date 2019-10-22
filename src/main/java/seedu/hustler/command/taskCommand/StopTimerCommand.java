package seedu.hustler.command.taskCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.ui.Ui;
import seedu.hustler.ui.timer.timerManager;
import seedu.hustler.schedule.RecommendedSchedule;

/**
 * Stops the timer and resets it.
 */
public class StopTimerCommand extends Command {
    
    /**
     * Updates the time spent on tasks and stops the timer.
     */
    public void execute() {
        RecommendedSchedule.confirm();
        timerManager.stopTimer();
    }
}
