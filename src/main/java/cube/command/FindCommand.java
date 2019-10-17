/**
 * This handles command that find a list of tasks with given keyword.
 *
 * @author tygq13
 */
package cube.command;

import cube.ui.Ui;
import cube.util.Parser;
import cube.util.Storage;
import cube.task.TaskList;
import cube.task.Task;

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
			String description = task.getDescription();
			String date = Parser.parseDateToString(task.getDate());
			if (description.contains(keyword) || date.contains(keyword)) {
				tasksFoundAt.add(task);
				break;
			}
		}
		ui.showFind(tasksFoundAt);
	}
}