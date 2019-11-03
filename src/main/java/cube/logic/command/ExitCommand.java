/**
 * The command signals exit to main process
 *
 * @author tygq13
 */
package cube.logic.command;

import cube.model.food.FoodList;
import cube.model.ModelManager;
import cube.storage.StorageManager;
import cube.logic.command.util.CommandResult;

public class ExitCommand extends Command{

	public static final String MESSAGE_SUCCESS = "Bye. Hope to see you again soon!";

	/**
	 * Signals that the user wishes to exit programme.
	 *
	 * @return True to signal exit programme.
	 */
	@Override
	public boolean isExit() {
		return true;
	}

	/**
	 * Signals exit to main engine, and provide exit feedback to user.
	 * @param model The facade of all models.
	 * @param storage The storage manager for commands.
	 * @return The command result with feedback to user.
	 */
	@Override
	public CommandResult execute(ModelManager model, StorageManager storage) {
		return new CommandResult(MESSAGE_SUCCESS, false, true);
	}
}