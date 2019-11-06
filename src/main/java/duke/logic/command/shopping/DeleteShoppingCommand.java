package duke.logic.command.shopping;

import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.message.ShoppingMessageUtils;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class DeleteShoppingCommand extends ShoppingCommand {

    public static final String COMMAND_WORD = "remove";

    private static final String MESSAGE_INGREDIENT_NOT_FOUND = "No ingredient found at index [%d].";

    private final Set<Index> indices;
    private ArrayList<Item<Ingredient>> toDeleteList;

    public DeleteShoppingCommand(Set<Index> indices) {
        requireNonNull(indices);
        this.indices = indices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        toDeleteList = new ArrayList<>();

        List<Item<Ingredient>> shoppingList = model.getFilteredShoppingList();

        for (Index index: indices) {
            if (index.getZeroBased() >= shoppingList.size()) {
                throw new CommandException(String.format(MESSAGE_INGREDIENT_NOT_FOUND, index.getOneBased()));
            }
            toDeleteList.add(shoppingList.get(index.getZeroBased()));
        }

        for (Item<Ingredient> toDelete : toDeleteList) {
            model.deleteShoppingList(toDelete);
        }

        model.commit(ShoppingMessageUtils.MESSAGE_COMMIT_REMOVE_SHOPPING);

        return new CommandResult(String.format(ShoppingMessageUtils.MESSAGE_SUCCESS_REMOVE_SHOPPING,
                indices.size()),
                CommandResult.DisplayedPage.SHOPPING);
    }
}

