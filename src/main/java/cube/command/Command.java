/**
 * This is an command interface used in interacting with users' command.
 *
 * @author tygq13
 */
package cube.command;

import cube.exception.CubeLoadingException;
import cube.task.TaskList;
import cube.ui.Ui;
import cube.util.Storage;
import cube.exception.DukeException;

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
	void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, CubeLoadingException;
}