package seedu.hustler.command;

import seedu.hustler.Hustler;
import seedu.hustler.ui.Ui;
import seedu.hustler.ui.timer.*;

public class StopTimerCommand extends Command {

    public void execute() {
        timerManager.stopTimer();
    }
}
