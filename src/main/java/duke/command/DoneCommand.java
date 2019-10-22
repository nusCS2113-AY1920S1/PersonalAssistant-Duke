package duke.command;


import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * duke.command.DoneCommand that deals with marking Tasks in the duke.tasklist.TaskList as done
 */
public class DoneCommand extends Command {
	private int index;

	public DoneCommand(String index) {
		this.index = Integer.parseInt(index) - 1;
	}

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
		if (index + 1 > tasks.size()) {
			throw new DukeException("There is no such task.");
		}
		Task t = tasks.get(index);
		t.markAsDone();
		tasks.set(index, t);
		ui.showLine("Congratulations on completing the following task:");
		ui.showLine(t.getDescription());
	}

	@Override
	public boolean isExit() {
		return false;
	}
}