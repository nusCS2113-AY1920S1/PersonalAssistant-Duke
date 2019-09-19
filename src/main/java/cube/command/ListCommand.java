/**
 * This handles command that list all the available tasks.
 *
 * @author tygq13
 */
package cube.command;

import cube.ui.Ui;
import cube.util.Storage;
import cube.task.TaskList;

public class ListCommand implements Command{

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
	 * Shows the whole list of tasks.
	 *
	 * @param tasks the list of tasks.
	 * @param ui the user interface to output message.
	 * @param storage storage of Duke.
	 */
	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) {
		ui.showList(tasks);
	}
}