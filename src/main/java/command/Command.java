/**
 * This is an command interface used in interacting with users' command.
 *
 * @author tygq13
 */
package command;

import task.TaskList;
import ui.Ui;
import util.Storage;
import exception.DukeException;

public interface Command {

	boolean isExit();

	/**
	 * Executes the command.
	 *
	 * @param tasks the list of tasks.
	 * @param ui the user interface to output message.
	 * @param storage storage of Duke.
	 * @throws DukeException general exception happens when using Duke.
	 */
	void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException;
}