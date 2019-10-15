package duke.logic.command.order;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;

public class ShowOrderCommand extends OrderCommand {
    public static final String COMMAND_WORD = "";

    private static final String MESSAGE_LIST_SUCCESS = "All orders are listed.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_LIST_SUCCESS, CommandResult.DisplayedPage.ORDER);
    }
}
