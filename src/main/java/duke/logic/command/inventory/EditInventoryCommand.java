package duke.logic.command.inventory;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.message.InventoryMessageUtils;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.exceptions.DuplicateEntityException;
import duke.model.inventory.Ingredient;

import java.util.List;

import static duke.commons.util.CollectionUtil.requireAllNonNull;

public class EditInventoryCommand extends InventoryCommand {

    public static final String COMMAND_WORD = "edit";

    private static final String MESSAGE_INDEX_OUT_OF_BOUND = "Index [%d] is out of bound.";

    public static final String AUTO_COMPLETE_INDICATOR = InventoryCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
        CliSyntax.PREFIX_INVENTORY_NAME,
        CliSyntax.PREFIX_INVENTORY_QUANTITY,
        CliSyntax.PREFIX_INVENTORY_REMARKS
    };

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
            throw new CommandException(String.format(MESSAGE_INDEX_OUT_OF_BOUND, index.getOneBased()));
        }

        Item<Ingredient> toEdit = lastShownList.get(index.getZeroBased());

        Item<Ingredient> edited = InventoryCommandUtil.createNewInventory(toEdit, inventoryDescriptor);
        try {
            model.setInventory(toEdit, edited);
        } catch (DuplicateEntityException e) {
            throw new CommandException(String.format(InventoryMessageUtils.MESSAGE_DUPLICATE_INVENTORY,
                    edited.getItem().getName()));
        }
        model.commit(InventoryMessageUtils.MESSAGE_COMMIT_EDIT_INVENTORY);

        return new CommandResult(String.format(InventoryMessageUtils.MESSAGE_SUCCESS_EDIT_INVENTORY,
                edited.getItem().getName()),
                CommandResult.DisplayedPage.INVENTORY);
    }
}
