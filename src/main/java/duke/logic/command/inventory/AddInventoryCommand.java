package duke.logic.command.inventory;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.inventory.Ingredient;
import duke.model.commons.Item;

import static java.util.Objects.requireNonNull;

public class AddInventoryCommand extends InventoryCommand {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_SUCCESS = "New ingredient added: %s";
    public static final String MESSAGE_DUPLICATE_INVENTORY = "%s already exists in the inventory list";

    private final Item<Ingredient> toAdd;

    public AddInventoryCommand(Item<Ingredient> toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasInventory(toAdd)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_INVENTORY, toAdd.getItem().getName()));
        }

        model.addInventory(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getItem().getName()),
                CommandResult.DisplayedPage.INVENTORY);
    }
}
