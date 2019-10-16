package seedu.hustler.command.avatarCommand;

import seedu.hustler.command.Command;
import seedu.hustler.ui.Ui;

/**
 * Command that checks the avatar's current status.
 */
public class CheckAvatarCommand extends Command {

    @Override
    public void execute() {
        Ui ui = new Ui();
        ui.showAvatarStatistics();
    }
}
