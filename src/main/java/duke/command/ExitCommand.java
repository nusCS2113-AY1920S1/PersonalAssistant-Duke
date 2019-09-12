package duke.command;

import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;

/**
 * duke.command.ExitCommand class which will save all the duke.task.Task data into the JSON file and initiate the exit procedure
 */
public class ExitCommand extends Command {
	public ExitCommand() {}

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException {
		storage.save(tasks);
	}

	@Override
	public boolean isExit() {
		return true;
	}
}