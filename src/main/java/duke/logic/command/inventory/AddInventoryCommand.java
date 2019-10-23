package duke.logic.command.inventory;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import static java.util.Objects.requireNonNull;

public class AddInventoryCommand extends InventoryCommand {

    public static final String COMMAND_WORD = "add";

    public static final String AUTO_COMPLETE_INDICATOR = InventoryCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
        CliSyntax.PREFIX_INVENTORY_NAME,
        CliSyntax.PREFIX_INVENTORY_QUANTITY,
        CliSyntax.PREFIX_INVENTORY_REMARKS
    };

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
