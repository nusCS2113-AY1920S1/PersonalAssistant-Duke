package seedu.hustler.command.taskCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.ui.Ui;
import seedu.hustler.ui.timer.*;

public class StopTimerCommand extends Command {

    public void execute() {
        timerManager.stopTimer();
    }
}
