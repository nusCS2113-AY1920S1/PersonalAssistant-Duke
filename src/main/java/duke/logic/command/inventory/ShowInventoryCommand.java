package duke.logic.command.inventory;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.message.InventoryMessageUtils;
import duke.model.Model;

/**
 * A command that displays the inventory list.
 */
public class ShowInventoryCommand extends InventoryCommand {

    public static final String COMMAND_WORD = "";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(InventoryMessageUtils.MESSAGE_SUCCESS_SHOW_INVENTORY,
                CommandResult.DisplayedPage.INVENTORY);
    }
}
