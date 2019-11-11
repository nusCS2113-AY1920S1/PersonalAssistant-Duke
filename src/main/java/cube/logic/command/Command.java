/**
 * This an abstract parent class of all commands.
 *
 * @author tygq13
 */
//@@author tygq13
package cube.logic.command;

import cube.model.ModelManager;
import cube.storage.StorageManager;
import cube.logic.command.exception.CommandException;
import cube.logic.command.util.CommandResult;

public abstract class Command {
	/**
	 * Indicates whethis this command signals exit. Default is false.
	 *
	 * @return false by defualt.
	 */
	public boolean isExit() {
		return false;
	}

	public abstract CommandResult execute(ModelManager model, StorageManager storage) throws CommandException;
}