/**
 * This handles command that deal with todo task. Implements Command interface.
 *
 * @author tygq13
 */
package command;

import ui.*;
import util.Storage;
import task.TaskList;
import task.Todo;
import exception.DukeException;

public class TodoCommand implements Command{
	private String description;

	/**
	 * Default constructor.
	 * Calls another constructor with (null) as argument.
	 */
	public TodoCommand() {
		this(null);
	}

	/**
	 * Constructor with one argument.
	 *
	 * @param description the description of the event.
	 */
	public TodoCommand(String description) {
		this.description = description;
	}

	/**
	 * Returns true if no error in the arguments of todo command, otherwise throws DukeException.
	 *
	 * @return true if deadline is valid.
	 * @throws DukeException printout warning when description or date of the todo command is empty.
	 */
	private boolean isValid() throws DukeException {
		if (description == null) {
			throw new DukeException(Message.EMPTY_DESCRIPTION);
		}
		return true;
	}

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
	 * Add an todo task to the task list and stores it.
	 * If the adding todo command is not valid, DukeException will be thrown.
	 * UI included to notify the user.
	 *
	 * @param tasks the list of tasks.
	 * @param ui the user interface to output message.
	 * @param storage storage of Duke.
	 * @throws DukeException exception happens during storage or if the command is not valid.
	 */
	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
		if (isValid()) {
			Todo t = new Todo(description);
			tasks.add(t);
			storage.append(t);
			ui.showAdd(tasks);
		}
	}
}