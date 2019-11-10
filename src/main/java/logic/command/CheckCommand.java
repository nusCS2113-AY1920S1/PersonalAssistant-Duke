package logic.command;

import common.DukeException;
import model.Model;

public class CheckCommand extends Command {

    private static final String NO_CRASH = "All tasks have proper deadlines.";
    private static final String CRASH = "Here are the busy days of some members: ";

    //@@author yuyanglin28
    /**
     * This method is to check deadline crash for each member.
     * @param model Model interface
     * @return crash tasks name or no crash message
     */
    @Override
    public CommandOutput execute(Model model) {
        String crash = model.check();
        if (crash.equals("")) {
            return new CommandOutput(NO_CRASH);
        } else {
            return new CommandOutput(CRASH + crash);
        }
    }
}
