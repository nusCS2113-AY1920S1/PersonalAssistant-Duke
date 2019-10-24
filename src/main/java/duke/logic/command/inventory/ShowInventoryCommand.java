package duke.logic.command.inventory;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;

/**
 * A command that displays the inventory list.
 */
public class ShowInventoryCommand extends InventoryCommand {
    public static final String COMMAND_WORD = "";

    private static final String MESSAGE_LIST_SUCCESS = "Showing the Inventory List.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_LIST_SUCCESS, CommandResult.DisplayedPage.INVENTORY);
    }
}
