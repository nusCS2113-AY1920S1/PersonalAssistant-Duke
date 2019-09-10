package command;

import ui.*;
import util.Storage;
import task.TaskList;
import task.Todo;
import exception.DukeException;

public class TodoCommand implements Command{
	private String description;

	public TodoCommand() {
		this(null);
	}

	public TodoCommand(String description) {
		this.description = description;
	}

	private boolean isValid() throws DukeException {
		if (description == null) {
			throw new DukeException(Message.EMPTY_DESCRIPTION);
		}
		return true;
	}

	@Override
	public boolean isExit() {
		return false;
	}

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