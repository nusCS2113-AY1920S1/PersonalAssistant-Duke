package seedu.hustler.command.taskCommand;

import seedu.hustler.command.Command;
import seedu.hustler.ui.timer.timerManager;

/**
 * Command that pauses the timer.
 */
public class PauseTimerCommand extends Command {
    /**
     * Pauses the timer.
     */
    public void execute() {
        timerManager.pauseTimer();
    }
}
