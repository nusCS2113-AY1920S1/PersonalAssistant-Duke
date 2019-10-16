package duke.logic.command.shopping;

import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class DeleteShoppingCommand extends ShoppingCommand {

    public static final String COMMAND_WORD = "remove";
    public static final String MESSAGE_SUCCESS = "%s is removed from the shopping list";
    public static final String MESSAGE_INVALID_INDEX = "Please enter a valid index";

    private final Index index;

    public DeleteShoppingCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Item<Ingredient>> shoppingList = model.getFilteredShoppingList();

        if (index.getZeroBased() >= shoppingList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        Item<Ingredient> toDelete = shoppingList.get(index.getZeroBased());

        model.deleteShoppingList(toDelete);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete.getItem().getName()),
                CommandResult.DisplayedPage.INVENTORY);
    }
}

