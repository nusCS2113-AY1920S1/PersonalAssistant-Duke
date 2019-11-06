package duke.logic.command.inventory;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.message.InventoryMessageUtils;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class DeleteInventoryCommand extends InventoryCommand {

    public static final String COMMAND_WORD = "remove";

    private static final String MESSAGE_INGREDIENT_NOT_FOUND = "No ingredient found at index [%d].";

    private final Set<Index> indices;
    private ArrayList<Item<Ingredient>> toDeleteList;

    public DeleteInventoryCommand(Set<Index> indices) {
        requireNonNull(indices);
        this.indices = indices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        toDeleteList = new ArrayList<>();

        List<Item<Ingredient>> inventoryList = model.getFilteredInventoryList();

        for (Index index: indices) {
            if (index.getZeroBased() >= inventoryList.size()) {
                throw new CommandException(String.format(MESSAGE_INGREDIENT_NOT_FOUND, index.getOneBased()));
            }
            toDeleteList.add(inventoryList.get(index.getZeroBased()));
        }

        for (Item<Ingredient> toDelete : toDeleteList) {
            model.deleteInventory(toDelete);
        }

        model.commit(InventoryMessageUtils.MESSAGE_COMMIT_REMOVE_INVENTORY);

        return new CommandResult(String.format(InventoryMessageUtils.MESSAGE_SUCCESS_REMOVE_INVENTORY,
                indices.size()),
                CommandResult.DisplayedPage.INVENTORY);
    }
}
