package duke.command;

import duke.DukeCore;
import duke.exception.DukeFatalException;

public class ByeCommand extends Command {
    @Override
    public void execute(DukeCore core) throws DukeFatalException {
        core.writeJsonFile();

        core.ui.showInfoDialog("Dr. Duke", "Thank you for using Dr. Duke. Hope to see you again soon!");
        core.stop();
    }
}
