package seedu.hustler.command.task;

import seedu.hustler.command.Command;
import seedu.hustler.ui.timer.timerManager;

/**
 * Command that stops the timer.
 */
public class StopTimerCommand extends Command {
    /**
     * Stops the timer.
     */
    public void execute() {
        timerManager.stopTimer();
    }
}
