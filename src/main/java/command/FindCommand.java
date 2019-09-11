/**
 * This handles command that find a list of tasks with given keyword.
 *
 * @author tygq13
 */
package command;

import ui.Ui;
import util.Storage;
import task.TaskList;
import task.Task;

public class FindCommand implements Command{
	private String keyword;
	public FindCommand(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * Always returns false since this is not an exit command.
	 *
	 * @return false.
	 */
	@Override
	public boolean isExit() {
		return false;
	}

	/**
	 * Shows a list of task with the specified keyword in description or date.
	 *
	 * @param tasks the list of tasks.
	 * @param ui the user interface to output message.
	 * @param storage storage of Duke.
	 */
	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) {
		TaskList tasksFoundAt = new TaskList();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			for (String description : task.getTask()) {
				if (description.contains(keyword)) {
					tasksFoundAt.add(task);
					break;
				}
			}
		}
		ui.showFind(tasksFoundAt);
	}
}