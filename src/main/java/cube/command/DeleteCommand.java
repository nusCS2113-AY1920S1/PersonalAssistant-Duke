/**
 * This handles command that deal with deletion of task.
 *
 * @author tygq13
 */
package command;

import ui.*;
import util.Storage;
import task.TaskList;
import task.Task;
import exception.DukeException;


public class DeleteCommand implements Command{
	String number;

	public DeleteCommand() {
		this(null);
	}

	public DeleteCommand(String number) {
		this.number = number;
	}

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

	@Override
	public boolean isExit() {
		return false;
	}

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException{
		if (isValid(tasks)) {
			int taskIndex = Integer.parseInt(number) - 1;
			Task t = tasks.get(taskIndex);
			tasks.remove(taskIndex);
			storage.save(tasks);
			ui.showRemove(t, tasks);
		}
	}
}