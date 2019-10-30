package duke.logic.command.inventory;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.message.InventoryMessageUtils;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ClearInventoryCommand extends InventoryCommand {

    public static final String COMMAND_WORD = "clear";

    private final List<Item<Ingredient>> emptyList;

    public ClearInventoryCommand() {
        emptyList = Collections.emptyList();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.clearInventory(emptyList);
        model.commit(InventoryMessageUtils.MESSAGE_SUCCESS_CLEAR_INVENTORY);

        return new CommandResult(String.format(InventoryMessageUtils.MESSAGE_SUCCESS_CLEAR_INVENTORY),
                CommandResult.DisplayedPage.INVENTORY);
    }
}
