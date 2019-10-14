package seedu.hustler.command.taskCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.ui.timer.timerManager;

public class ShowTimerCommand extends Command {

    public void execute() {
        Hustler.timermanager.printTimeLeft();
    }
}
