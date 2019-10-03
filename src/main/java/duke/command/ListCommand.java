package duke.command;

import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * duke.command.ListCommand class which executes the command of displaying the duke.tasklist.TaskList to the user
 */
public class ListCommand extends Command {
	public ListCommand() {}

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) {
		if (tasks.size() > 0) {
			ui.showLine("Here are the tasks in your list:");
			for (int i = 0; i < tasks.size(); i++) {
				ui.showEntry(i + 1, tasks.get(i));
			}
		} else {
			ui.showLine("There are no tasks in your list.");
		}
	}

	@Override
	public boolean isExit() {
		return false;
	}
}