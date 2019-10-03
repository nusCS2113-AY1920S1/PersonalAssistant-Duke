package duke.command;

import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

/**
 * duke.command.FindCommand which executes the procedure for searching for duke.task.Task objects in the duke.tasklist.TaskList which match the
 * keyword given by the user and shows the user a list of matching Tasks or else informs the user that no
 * matching duke.task.Task objects were found
 */
public class FindCommand extends Command {
	private String keyword;

	public FindCommand(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) {
		if (tasks.size() == 0) {
			ui.showLine("You have no tasks in your list! :-)");
		} else {
			tasks.find(keyword, ui);
		}
	}

	@Override
	public boolean isExit() {
		return false;
	}
}
