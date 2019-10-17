/**
 * This handles command that deal with event task. Implements Command interface.
 *
 * @author tygq13
 */
package cube.command;

import java.util.Date;

import cube.task.Task;
import cube.ui.*;
import cube.util.Storage;
import cube.task.TaskList;
import cube.task.Event;
import cube.exception.DukeException;

public class EventCommand implements Command{
	private String description;
	private Date date;

	/**
	 * Default constructor.
	 * Calls another constructor with (null) as argument.
	 */
	public EventCommand() {
		this(null);
	}

	/**
	 * Constructor with one argument.
	 * Call another constructor with additional argument date=null.
	 *
	 * @param description the description of the event.
	 */
	public EventCommand(String description) {
		this(description, null);
	}

	/**
	 * Constructor with two arguments.
	 *
	 * @param description the description of the event
	 * @param date the date of the event.
	 */
	public EventCommand(String description, Date date) {
		this.description = description;
		this.date = date;
	}

	/**
	 * Returns true if no error in the arguments of event command, otherwise throws DukeException.
	 *
	 * @return true if deadline is valid.
	 * @throws DukeException printout warning when description or date of the event command is empty.
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
		return false;
	}

	/**
	 * Add an event to the task list and stores it.
	 * If the adding event command is not valid, DukeException will be thrown.
	 * UI included to notify the user.
	 *
	 * @param tasks the list of tasks.
	 * @param ui the user interface to output message.
	 * @param storage storage of Duke.
	 * @throws DukeException exception happens during storage or if the command is not valid.
	 */
	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException{
		if (isValid() && !isAnomaly(tasks, date)) {
			Event e = new Event(description, date);
			tasks.add(e);
			storage.append(e);
			ui.showAdd(tasks);
		}
	}
}