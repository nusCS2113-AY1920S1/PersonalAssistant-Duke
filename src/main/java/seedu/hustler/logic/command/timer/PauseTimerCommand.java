package seedu.hustler.logic.command.timer;

import seedu.hustler.logic.command.Command;
import seedu.hustler.ui.timer.TimerManager;

/**
 * Command that pauses the timer.
 */
public class PauseTimerCommand extends Command {
    /**
     * Pauses the timer.
     */
    public void execute() {
        TimerManager.pauseTimer();
    }
}
