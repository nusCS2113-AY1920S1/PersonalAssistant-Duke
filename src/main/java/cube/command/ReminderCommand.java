/**
 * This handles the reminder command.
 *
 * @author tygq13
 */
package cube.command;

import java.util.Date;
import java.util.Calendar;
import cube.ui.Ui;
import cube.util.Storage;
import cube.task.TaskList;
import cube.task.Task;

public class ReminderCommand implements Command{

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
	 * Shows the list of tasks due in 10 days.
	 *
	 * @param tasks the list of tasks.
	 * @param ui the user interface to output message.
	 * @param storage storage of Duke.
	 */
	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) {
		TaskList tasksReminder = new TaskList();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date()); // get current time
		cal.add(Calendar.DATE, 7); // deadline within 7 days
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			Date taskDate = task.getDate();
			if (taskDate != null && taskDate.before(cal.getTime())) {
				tasksReminder.add(task);
			}
		}
		//ui.showReminder(tasksReminder);
	}
}