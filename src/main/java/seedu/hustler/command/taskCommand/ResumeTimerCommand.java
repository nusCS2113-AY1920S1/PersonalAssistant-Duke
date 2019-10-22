package seedu.hustler.command.taskCommand;

import seedu.hustler.command.Command;
import seedu.hustler.ui.timer.timerManager;

/**
 * Command that resumes the timer.
 */
public class ResumeTimerCommand extends Command {
    /**
     * Resumes the timer.
     */
    public void execute() {
        timerManager.resumeTimer();
    }
}
