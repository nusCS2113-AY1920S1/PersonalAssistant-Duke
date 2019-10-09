package duke.logic.command.inventory;

import duke.logic.command.Command;

/**
 * A abstract base class for commands that manages orders.
 */
public abstract class InventoryCommand extends Command {
    public static final String COMMAND_WORD = "inv";
}