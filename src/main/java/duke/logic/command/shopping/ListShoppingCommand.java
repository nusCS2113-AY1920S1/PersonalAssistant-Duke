package duke.logic.command.shopping;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;

public class ListShoppingCommand extends ShoppingCommand {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listing the ingredients to be bought";

    @Override
    public CommandResult execute(Model model) throws CommandException {

        model.updateFilteredShoppingList(Model.PREDICATE_SHOW_AVAILABLE_SHOPPING);

        return new CommandResult(String.format(MESSAGE_SUCCESS),
                CommandResult.DisplayedPage.SHOPPING);
    }
}
