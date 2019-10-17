package duke.logic.command.shopping;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static duke.commons.util.CollectionUtil.requireAllNonNull;

public class EditShoppingCommand extends ShoppingCommand {

    public static final String COMMAND_WORD = "edit";
    public static final String MESSAGE_SUCCESS = "Edited Ingredient %s in the shopping list";

    public final Index index;
    public final ShoppingDescriptor shoppingDescriptor;

    public EditShoppingCommand(Index index, ShoppingDescriptor edited) {
        requireAllNonNull(index, edited);

        this.index = index;
        shoppingDescriptor = edited;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Item<Ingredient>> lastShownList = model.getFilteredShoppingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Message.MESSAGE_INVALID_INDEX);
        }

        Item<Ingredient> toEdit = lastShownList.get(index.getZeroBased());

        Item<Ingredient> edited = ShoppingCommandUtil.createNewIngredient(toEdit, shoppingDescriptor);

        model.setShoppingList(toEdit, edited);
        model.updateFilteredShoppingList(Model.PREDICATE_SHOW_ALL_SHOPPING);

        return new CommandResult(String.format(MESSAGE_SUCCESS, edited.getItem().getName()),
                CommandResult.DisplayedPage.INVENTORY);
    }
}