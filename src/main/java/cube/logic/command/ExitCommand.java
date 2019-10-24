//@@author LL-Pengfei
/**
 * ExitCommand.java
 * This class supports command dealing with Programme Exit.
 */
package cube.logic.command;

import cube.model.FoodList;
import cube.storage.StorageManager;

/**
 * This Class supports command dealing with Programme Exit.
 */
public class ExitCommand extends Command{

	private final String MESSAGE_SUCCESS = "Bye. Hope to see you again soon!";

	/**
	 * This method returns true. When called, the user wishes to exit the programme.
	 *
	 * @return True to signal exit programme.
	 */
	@Override
	public boolean isExit() {
		return true;
	}

	/**
	 * This method provides feedback to user message for Programme Exit.
	 *
	 * @param list The food list.
	 * @param storage The current Storage.
	 * @return The message feedback to user before Programme Exit.
	 */
	@Override
	public CommandResult execute(FoodList list, StorageManager storage) {
		return new CommandResult(MESSAGE_SUCCESS, false, true);
	}
}