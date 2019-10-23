package duke.logic.command.inventory;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import java.util.List;

import static duke.commons.util.CollectionUtil.requireAllNonNull;

public class EditInventoryCommand extends InventoryCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String AUTO_COMPLETE_INDICATOR = InventoryCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
        CliSyntax.PREFIX_INVENTORY_NAME,
        CliSyntax.PREFIX_INVENTORY_QUANTITY,
        CliSyntax.PREFIX_INVENTORY_REMARKS
    };

    public static final String MESSAGE_SUCCESS = "Edited Ingredient %s in the inventory list";

    public final Index index;
    public final InventoryDescriptor inventoryDescriptor;

    public EditInventoryCommand(Index index, InventoryDescriptor edited) {
        requireAllNonNull(index, edited);

        this.index = index;
        inventoryDescriptor = edited;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);
        List<Item<Ingredient>> lastShownList = model.getFilteredInventoryList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Message.MESSAGE_INVALID_INDEX);
        }

        Item<Ingredient> toEdit = lastShownList.get(index.getZeroBased());

        Item<Ingredient> edited = InventoryCommandUtil.createNewInventory(toEdit, inventoryDescriptor);

        model.setInventory(toEdit, edited);
        model.updateFilteredInventoryList(Model.PREDICATE_SHOW_ALL_INVENTORY);

        return new CommandResult(String.format(MESSAGE_SUCCESS, edited.getItem().getName()),
                CommandResult.DisplayedPage.INVENTORY);
    }
}
