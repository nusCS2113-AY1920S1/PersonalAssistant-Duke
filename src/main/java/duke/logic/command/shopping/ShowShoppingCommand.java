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

    /**
     * Executes the show shopping command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {

        model.updateFilteredShoppingList(Model.PREDICATE_SHOW_ALL_SHOPPING);

        return new CommandResult(ShoppingMessageUtils.MESSAGE_SUCCESS_SHOW_SHOPPING,
                CommandResult.DisplayedPage.SHOPPING);
    }
}