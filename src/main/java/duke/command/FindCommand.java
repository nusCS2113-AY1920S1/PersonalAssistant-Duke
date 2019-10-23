package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.TaskListPrinter;
import duke.ui.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * duke.command.FindCommand which executes the procedure for searching for duke.task.Task objects in the duke.tasklist.TaskList which match the
 * keyword given by the user and shows the user a list of matching Tasks or else informs the user that no
 * matching duke.task.Task objects were found
 */
public class FindCommand extends Command {
	private String keyword;
	private Command listCommand;

	public FindCommand(String keyword, Optional<String> filter) {
		this.keyword = keyword;
		this.listCommand = new ListCommand(filter);
	}

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws ParseException, IOException, DukeException {
		if (tasks.size() == 0) {
			ui.showLine("You have no tasks in your list! :-)");
		} else {
			ArrayList<Task> foundTasksTemp = new ArrayList<Task>();
			for (int i=0; i<tasks.size(); i++) {
				Task currentTask = tasks.get(i);
				if (currentTask.getDescription().contains(keyword)) {
					foundTasksTemp.add(currentTask);
				}
			}
			TaskList foundTasks = new TaskList(foundTasksTemp);
			listCommand.execute(foundTasks, ui, storage);
		}
	}

	@Override
	public boolean isExit() {
		return false;
	}
}
