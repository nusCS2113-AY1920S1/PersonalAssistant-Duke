package duke.logic.command.inventory;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import java.util.Collections;
import java.util.List;

public class ClearInventoryCommand extends InventoryCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Inventory list is cleared";

    private final List<Item<Ingredient>> emptyList;

    public ClearInventoryCommand() {
        emptyList = Collections.emptyList();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.clearInventory(emptyList);

        return new CommandResult(String.format(MESSAGE_SUCCESS),
                CommandResult.DisplayedPage.INVENTORY);
    }
}
