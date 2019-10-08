package seedu.hustler.command;

import seedu.hustler.Hustler;
import seedu.hustler.ui.timer.timerManager;

public class ShowTimerCommand extends Command {

    public void execute() {
        Hustler.timermanager.printTimeLeft();
    }
}
