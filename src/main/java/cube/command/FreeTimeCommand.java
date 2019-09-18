/**
 * This handles command that request for free time of n hours.
 *
 * @author tygq13
 */
package cube.command;

import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import cube.ui.Ui;
import cube.util.Storage;
import cube.task.TaskList;
import cube.task.Task;

public class FreeTimeCommand implements Command{
	private String number;

	/**
	 * Default constructor.
	 * Calls another constructor with (null) as argument.
	 */
	public FreeTimeCommand() {
		this(null);
	}

	/**
	 * Constructor with one argument.
	 *
	 * @param number the task number in the task list to be marked as done.
	 */
	public FreeTimeCommand(String number) {
		this.number = number;
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
	 * Shows the next free day.
	 *
	 * @param tasks the list of tasks.
	 * @param ui the user interface to output message.
	 * @param storage storage of Duke.
	 */
	@Override
	// for simplicity's sake, just show the next free day regardless of number of free hours
	public void execute(TaskList tasks, Ui ui, Storage storage) {
		// copy by value
		ArrayList<Task> list = new ArrayList<>();
		for(int i = 0; i < tasks.size(); i++) {
			list.add(tasks.get(i));
		}
		Collections.sort(list, new Comparator<Task>() {
		  public int compare(Task o1, Task o2) {
		      if (o1.getDate() == null || o2.getDate() == null)
		        return 0;
		      return o1.getDate().compareTo(o2.getDate());
		  }
		});
		Date today = new Date();
		Calendar nextPossibleDay = Calendar.getInstance();
		nextPossibleDay.setTime(today);
		nextPossibleDay.add(Calendar.DATE, 1);
		for(int i = 0; i < list.size(); i++) {
			Task task = list.get(i);
			Date date = task.getDate();
			if (date != null && date.after(today) && date.compareTo(nextPossibleDay.getTime()) == 0) {
				nextPossibleDay.add(Calendar.DATE, 1);
			}
		}
		ui.showFreeDay(nextPossibleDay.getTime());
	}
}