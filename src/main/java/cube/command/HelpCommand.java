/**
 * This handles command that asks for help by showing a list of available command and their usage.
 *
 * @author tygq13
 */
package command;

import ui.Ui;
import util.Storage;
import task.TaskList;

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
	 * @param storage storage of Duke.
	 */
	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) {
		ui.showHelp();
	}
}