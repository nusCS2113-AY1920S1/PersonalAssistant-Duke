package duke.logic.command.shopping;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;

/**
 * A command that displays the shopping list.
 */
public class ShowShoppingCommand extends ShoppingCommand {
    public static final String COMMAND_WORD = "";

    private static final String MESSAGE_LIST_SUCCESS = "Showing Shopping List.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_LIST_SUCCESS, CommandResult.DisplayedPage.SHOPPING);
    }
}