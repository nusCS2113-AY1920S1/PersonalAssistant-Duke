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

    /**
     * Executes the show inventory command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(InventoryMessageUtils.MESSAGE_SUCCESS_SHOW_INVENTORY,
                CommandResult.DisplayedPage.INVENTORY);
    }
}
