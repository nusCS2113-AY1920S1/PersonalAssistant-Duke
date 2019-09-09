package command;

import ui.*;
import util.Storage;
import task.TaskList;
import task.Task;
import exception.DukeException;


public class DoneCommand implements Command{
	String number;

	public DoneCommand() {
		this(null);
	}

	public DoneCommand(String number) {
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
			t.markAsDone();
			storage.save(tasks);
			ui.showDone(t);
		}
	}
}