package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;

/**
 * duke.command.DeleteCommand which executes the procedure for deleting duke.task.Task objects from the duke.tasklist.TaskList
 */
public class DeleteCommand extends Command {
	private int index;

	public DeleteCommand(String index) {
		this.index = Integer.parseInt(index);
	}

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
		if (index > tasks.size()) {
			throw new DukeException("There is no such task.");
		}
		ui.showLine("You have removed this task:");
		Task t = tasks.get(index - 1);
		ui.showLine(t.getDescription());
		tasks.remove(index - 1);
		if (tasks.size() == 1) {
			ui.showLine("Now you have 1 task in the list.");
		} else {
			ui.showLine("Now you have " + tasks.size() + " tasks in the list.");
		}
		storage.save(tasks);
	}

	@Override
	public boolean isExit() {
		return false;
	}
}