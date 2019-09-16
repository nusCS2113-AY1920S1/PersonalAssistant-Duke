/**
 * This handles command that exits the programme.
 *
 * @author tygq13
 */
package command;

import exception.DukeException;
import ui.Ui;
import util.Storage;
import task.TaskList;;

public class ExitCommand implements Command{

	/**
	 * Always returns true since this is an exit command.
	 *
	 * @return true.
	 */
	@Override
	public boolean isExit() {
		return true;
	}

	/**
	 * Exit the programme.
	 * UI included to notify the user.
	 *
	 * @param tasks the list of tasks.
	 * @param ui the user interface to output message.
	 * @param storage storage of Duke.
	 * @throws DukeException exception happens during storage.
	 */
	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
		storage.save(tasks);
		ui.showExit();
	}
}