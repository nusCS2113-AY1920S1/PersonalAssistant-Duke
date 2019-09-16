/**
 * This handles command that mark a task as done.
 *
 * @author tygq13
 */
package command;

import ui.*;
import util.Storage;
import task.TaskList;
import task.Task;
import exception.DukeException;


public class DoneCommand implements Command{
	String number;

	/**
	 * Defualt constructor.
	 * Calls another constructor with (null) as argument.
	 */
	public DoneCommand() {
		this(null);
	}

	/**
	 * Constructor with one argument.
	 *
	 * @param number the task number in the task list to be marked as done.
	 */
	public DoneCommand(String number) {
		this.number = number;
	}

	/**
	 * Returns true if the arguments for task command is valid, otherwise throws relevant duke exception.
	 *
	 * @param list the list of task.
	 * @return true if command is valid.
	 * @throws DukeException exception happens when task number is empty or invalid.
	 */
	private boolean isValid(TaskList list) throws DukeException{
		if (number == null) {
			throw new DukeException(Message.EMPTY_TASK_NUMBER);
		}
		try {
			int taskNumber = Integer.parseInt(number);
			if (taskNumber > list.size() || taskNumber <= 0) {
				throw new DukeException(Message.INVALID_TASK_NUMBER);
			}
		} catch (NumberFormatException e) {
			throw new DukeException(Message.INVALID_TASK_NUMBER);
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
	 * Marks the task with the specified task number as done, and stores the status.
	 * If command is not valid, DukeException will be thrown.
	 * UI included to notify the user.
	 *
	 * @param tasks the list of tasks.
	 * @param ui the user interface to output message.
	 * @param storage storage of Duke.
	 * @throws DukeException exception happens during storage or if the command is not valid.
	 */
	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException{
		if (isValid(tasks)) {
			int taskIndex = Integer.parseInt(number) - 1;
			Task t = tasks.get(taskIndex);
			t.markAsDone();
			storage.save(tasks);
			ui.showDone(t);
		}
	}
}