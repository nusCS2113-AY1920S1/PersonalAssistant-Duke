package duke.logic.command.shopping;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.message.ShoppingMessageUtils;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class DeleteShoppingCommand extends ShoppingCommand {

    public static final String COMMAND_WORD = "remove";

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
            throw new CommandException(Message.MESSAGE_INVALID_INDEX);
        }

        Item<Ingredient> toDelete = shoppingList.get(index.getZeroBased());

        model.deleteShoppingList(toDelete);
        model.commit(ShoppingMessageUtils.MESSAGE_COMMIT_REMOVE_SHOPPING);

        return new CommandResult(String.format(ShoppingMessageUtils.MESSAGE_SUCCESS_REMOVE_SHOPPING,
                toDelete.getItem().getName()),
                CommandResult.DisplayedPage.SHOPPING);
    }
}

