package seedu.hustler.command.taskCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;

/**
 * Command that shows the time remaining of timer.
 */
public class ShowTimerCommand extends Command {
    /**
     * Shows the time remaining of the timer.
     */
    public void execute() {
        Hustler.timermanager.checkTimeLeft();
    }
}
