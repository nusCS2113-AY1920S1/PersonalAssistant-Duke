/**
 * This handles command that asks for help by showing a list of available command and their usage.
 *
 * @author tygq13
 */
package cube.command;

import cube.ui.Ui;
import cube.util.Storage;
import cube.task.TaskList;

public class HelpCommand implements Command{

	/**
	 * Always returns false since this is not an exit command.
	 *
	 * @return false.
	 */
	@Override
	public boolean isExit() {
		return false;
	}

	/**
	 * Shows the list of available command.
	 *
	 * @param tasks the list of tasks.
	 * @param ui the user interface to output message.
	 * @param storage storage of Cube.
	 */
	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) {
		ui.showHelp();
	}
}