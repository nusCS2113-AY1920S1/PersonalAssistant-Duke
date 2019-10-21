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
	 * The class returns true. When called, the user wishes to exit the programme.
	 *
	 * @return True to signal exit programme.
	 */
	@Override
	public boolean isExit() {
		return true;
	}
	
	@Override
	public CommandResult execute(FoodList list, StorageManager storage) {
		return new CommandResult(MESSAGE_SUCCESS, false, true);
	}
}