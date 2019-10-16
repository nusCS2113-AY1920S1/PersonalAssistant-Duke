package duke.logic.command.shopping;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import java.util.Collections;
import java.util.List;

public class ClearShoppingCommand extends ShoppingCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Shopping list is cleared";

    private final List<Item<Ingredient>> emptyList;

    public ClearShoppingCommand() {
        emptyList = Collections.emptyList();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.clearShoppingList(emptyList);

        return new CommandResult(String.format(MESSAGE_SUCCESS),
                CommandResult.DisplayedPage.INVENTORY);
    }
}