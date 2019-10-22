package seedu.hustler.command.timer;

import seedu.hustler.command.Command;
import seedu.hustler.ui.timer.TimerManager;

/**
 * Command that stops the timer.
 */
public class StopTimerCommand extends Command {
    /**
     * Stops the timer.
     */
    public void execute() {
        TimerManager.stopTimer();
    }
}
