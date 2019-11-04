package logic.command;

import common.DukeException;
import model.Model;

public class CheckCommand extends Command {

    private static final String NO_CRASH = "All tasks have proper ddls.";
    private static final String CRASH = "Here are the busy days of some members: ";

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        String crash = model.check();
        if (crash.equals("")) {
            return new CommandOutput(NO_CRASH);
        } else {
            return new CommandOutput(CRASH + crash);
        }
    }
}
