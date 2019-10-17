/**
 * This handles command that deal with deadline task. Implements Command interface.
 *
 * @author tygq13
 */
package cube.command;

import java.util.Date;

import cube.task.Task;
import cube.ui.*;
import cube.util.Storage;
import cube.task.TaskList;
import cube.task.Deadline;
import cube.exception.DukeException;

public class DeadlineCommand implements Command{
	private String description;
	private Date date;

	/**
	 * Default Constructor.
	 * Calls another constructor with (null) as argument.
	 */
	public DeadlineCommand() {
		this(null);
	}

	/**
	 * Constructor with one argument.
	 * Calls another constructor with additional argument date=null.
	 *
	 * @param description the description of the task.
	 */
	public DeadlineCommand(String description) {
		this(description, null);
	}

	/**
	 * Constructor with two argument.
	 *
	 * @param description the description of the task.
	 * @param date the deadline date of the task.
	 */
	public DeadlineCommand(String description, Date date) {
		this.description = description;
		this.date = date;
	}

	/**
	 * Returns true if no error in the arguments of deadline command, otherwise throws DukeException.
	 *
	 * @return true if deadline is valid.
	 * @throws DukeException printout warning when description or date of the deadline command is empty.
	 */
	private boolean isValid() throws DukeException{
		if (description == null) {
			throw new DukeException(Message.EMPTY_DESCRIPTION);
		}
		if (date == null) {
			throw new DukeException(Message.EMPTY_DATE);
		}
		return true;
	}

	/**
	 * Checks if the description or date already exists in tasklist.
	 * @param tasks the list of tasks.
	 * @param date the date of task to check with.
	 * @return false if there are no clashes of the description or date.
	 * @throws DukeException
	 */
	private boolean isAnomaly(TaskList tasks, Date date) throws DukeException {
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			Date task_date = task.getDate();
			if (date.equals(task_date)) {
				throw new DukeException(Message.EXISTING_DATE);
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
		// interface can clearer, but parent class can reduce repeating code, which is better?
		return false;
	}

	/**
	 * Adds a deadline task to task list and stores it.
	 * If the adding deadline command is not valid, an DukeException will be thrown.
	 * UI is included to notify the user.
	 *
	 * @param tasks the list of tasks.
	 * @param ui the user interface to output message.
	 * @param storage storage of Duke.
	 * @throws DukeException exception happens during storage, or if the command is not valid.
	 */
	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
		if (isValid() && !isAnomaly(tasks, date)) {
			Deadline d = new Deadline(description, date);
			tasks.add(d);
			storage.append(d);
			ui.showAdd(tasks);
		}
	}
}