package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.text.ParseException;

public class UndoCommand extends Command {
	private UndoStack undoStack;

	public UndoCommand(UndoStack undoStack) {
		this.undoStack = undoStack;
	}

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, ParseException, DukeException {
		if (undoStack.isEmpty()) {
			throw new DukeException("There are no actions to undo!");
		}
		Command mostRecent = undoStack.retrieveRecent();
		mostRecent.execute(tasks, ui, storage);
	}

	@Override
	public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {

	}
}
