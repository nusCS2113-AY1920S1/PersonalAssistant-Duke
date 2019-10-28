package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

public class SetCommand extends Command {
	private Optional<String> filter;
	private int index;
	private Task t;

	public SetCommand(Optional<String> filter, int index, Task t) {
		this.filter = filter;
		this.index = index;
		this.t = t;
	}

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, ParseException, DukeException {
		tasks.set(filter, index, t);
	}

	@Override
	public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {}
}
