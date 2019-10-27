package seedu.hustler.command.avatar;

import seedu.hustler.command.Command;
import seedu.hustler.ui.Ui;

/**
 * Command that checks the avatar's current status.
 */
public class CheckAvatarCommand extends Command {

    public CheckAvatarCommand() {

    }

    @Override
    public void execute() {
        Ui ui = new Ui();
        ui.showAvatarStatistics();
    }
}
