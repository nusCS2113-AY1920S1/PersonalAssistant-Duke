package duke.logic.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

public class ReplaceCommand extends Command {
	private TaskList taskList;

	public ReplaceCommand(TaskList tasks) {
		this.taskList = tasks;
	}

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, ParseException, DukeException {
		tasks.replace(taskList);
		storage.save(tasks);
	}

	@Override
	public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {
	}
}
