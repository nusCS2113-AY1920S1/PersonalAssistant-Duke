/**
 * This handles command that deal with todo task. Implements Command interface.
 *
 * @author tygq13
 */
package cube.command;

import cube.task.Task;
import cube.ui.*;
import cube.util.Storage;
import cube.task.TaskList;
import cube.task.Todo;
import cube.exception.DukeException;

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
	 * Checks if the description or date already exists in tasklist.
	 * @param tasks the list of tasks.
	 * @param description the description of task to check with.
	 * @return false if there are no clashes of the description or date.
	 * @throws DukeException
	 */
	private boolean isAnomaly(TaskList tasks, String description) throws DukeException {
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			String[] task_components = task.getTask();
			if (task_components.length == 1 && description.equals(task_components[0])) {
				throw new DukeException(Message.EXISTING_DESCRIPTION);
			}
		}
		return false;
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
		if (isValid() && !isAnomaly(tasks, description)) {
			Todo t = new Todo(description);
			tasks.add(t);
			storage.append(t);
			ui.showAdd(tasks);
		}
	}
}