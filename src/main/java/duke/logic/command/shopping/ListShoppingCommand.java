package duke.logic.command.shopping;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.message.ShoppingMessageUtils;
import duke.model.Model;

import static java.util.Objects.requireNonNull;

public class ListShoppingCommand extends ShoppingCommand {

    public static final String COMMAND_WORD = "list";

    /**
     * Executes the list shopping command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredShoppingList(Model.PREDICATE_SHOW_AVAILABLE_SHOPPING);

        return new CommandResult(String.format(ShoppingMessageUtils.MESSAGE_SUCCESS_lIST_SHOPPING),
                CommandResult.DisplayedPage.SHOPPING);
    }
}
