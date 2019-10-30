package duke.logic.command.shopping;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.message.ShoppingMessageUtils;
import duke.model.Model;

import static java.util.Objects.requireNonNull;

public class ListShoppingCommand extends ShoppingCommand {

    public static final String COMMAND_WORD = "list";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.updateFilteredShoppingList(Model.PREDICATE_SHOW_AVAILABLE_SHOPPING);

        return new CommandResult(String.format(ShoppingMessageUtils.MESSAGE_SUCCESS_lIST_SHOPPING),
                CommandResult.DisplayedPage.SHOPPING);
    }
}
