/**
 * This handles command that request for free time of n hours.
 *
 * @author tygq13
 */
package command;

import ui.Ui;
import util.Storage;
import task.TaskList;
import task.Task;

public class FreeTimeCommand implements Command{
	private String number;

	/**
	 * Defualt constructor.
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
	 * Returns true if the arguments for FreeTime command is valid, otherwise throws relevant duke exception.
	 *
	 * @param list the list of task.
	 * @return true if command is valid.
	 * @throws DukeException exception happens when hour number is empty or invalid.
	 */
	//todo: free time with hour number argument is not very useful, try others in new project
	private boolean isValid() throws DukeException{
		if (number == null) {
			throw new DukeException(Message.EMPTY_HOUR_NUMBER);
		}
		try {
			int hourNumber = Integer.parseInt(number);
			if (hourNumber > 24 || hourNumber <= 0) {
				throw new DukeException(Message.INVALID_HOUR_NUMBER);
			}
		} catch (NumberFormatException e) {
			throw new DukeException(Message.INVALID_HOUR_NUMBER);
		}
		return true;
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
		ArrayList list = new ArrayList(tasks); // copy by value
		Collections.sort(myList, new Comparator<Task>() {
		  public int compare(Task o1, Task o2) {
		      if (o1.getDate() == null || o2.getDate() == null)
		        return 0;
		      return o1.getDate().compareTo(o2.getDate());
		  }
		});
		for ()
		ui.showFind(task);
	}
}