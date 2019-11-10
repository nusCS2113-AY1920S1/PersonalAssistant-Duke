package duke.logic.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;

public class ReplaceCommand extends Command {
	private ArrayList<Task> taskList;
	private Optional<String> filter;

	public ReplaceCommand(ArrayList<Task> tasks, Optional<String> filter) {
		this.taskList = new ArrayList<>(tasks);
		this.filter = filter;
	}

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, ParseException, DukeException {
		tasks.replace(taskList);
		if (filter.isPresent()) {
			ui.showLine(filter.get() + " clear command has been undone!");
		} else {
			ui.showLine("Clear command has been undone!");
		}
		storage.save(tasks);
	}

	@Override
	public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {
	}
}
