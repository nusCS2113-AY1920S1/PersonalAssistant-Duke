package duke.logic.command.shopping;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.message.ShoppingMessageUtils;
import duke.model.Model;

/**
 * A command that displays the shopping list.
 */
public class ShowShoppingCommand extends ShoppingCommand {

    public static final String COMMAND_WORD = "";

    @Override
    public CommandResult execute(Model model) throws CommandException {

        model.updateFilteredShoppingList(Model.PREDICATE_SHOW_ALL_SHOPPING);

        return new CommandResult(ShoppingMessageUtils.MESSAGE_SUCCESS_SHOW_SHOPPING,
                CommandResult.DisplayedPage.SHOPPING);
    }
}