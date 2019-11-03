package logic.command;

import common.DukeException;
import model.Model;

public class ByeCommand extends Command {
    private static final String BYE_MESSAGE = "Bye. Hope to see you again soon!";

    //@@author yuyanglin28
    @Override
    public CommandOutput execute(Model model) throws DukeException {
        return new CommandOutput(BYE_MESSAGE,true);
    }
}
