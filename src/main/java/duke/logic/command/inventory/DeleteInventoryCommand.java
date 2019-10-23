package duke.logic.command.inventory;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class DeleteInventoryCommand extends InventoryCommand {

    public static final String COMMAND_WORD = "remove";
    public static final String MESSAGE_SUCCESS = "%s is removed from the inventory list";

    private final Index index;

    public DeleteInventoryCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Item<Ingredient>> inventoryList = model.getFilteredInventoryList();

        if (index.getZeroBased() >= inventoryList.size()) {
            throw new CommandException(Message.MESSAGE_INVALID_INDEX);
        }

        Item<Ingredient> toDelete = inventoryList.get(index.getZeroBased());

        model.deleteInventory(toDelete);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete.getItem().getName()),
                CommandResult.DisplayedPage.INVENTORY);
    }
}
