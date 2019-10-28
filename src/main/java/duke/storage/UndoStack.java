package duke.storage;

import duke.command.Command;
import duke.task.Task;

import java.util.Stack;

public class UndoStack {
	private Stack<Command> memory;

	public UndoStack() {
		memory = new Stack<>();
	}

	public void addAction(Command action) {
		memory.push(action);
	}

	public Command retrieveRecent() {
		return memory.pop();
	}

	public boolean isEmpty() {
		return memory.isEmpty();
	}
}
