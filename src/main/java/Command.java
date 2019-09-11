import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Abstract class Command which dictates two necessary methods in all Command subclasses
 * method execute which will execute whatever the user input requests
 * method isExit which checks whether the user input will result in the program shutting down
 */
public abstract class Command {
	public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, ParseException, DukeException;
	public abstract boolean isExit();
}

/**
 * ExitCommand class which will save all the Task data into the JSON file and initiate the exit procedure
 */
class ExitCommand extends Command {
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

/**
 * ListCommand class which executes the command of displaying the TaskList to the user
 */
class ListCommand extends Command {
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

/**
 * DeleteCommand which executes the procedure for deleting Task objects from the TaskList
 */
class DeleteCommand extends Command {
	private int index;

	public DeleteCommand(String index) {
		this.index = Integer.parseInt(index);
	}

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
		if (index > tasks.size()) {
			throw new DukeException("There is no such task.");
		}
		ui.showLine("Noted. I've removed this task:");
		Task t = tasks.get(index - 1);
		ui.showLine("  " + t);
		tasks.remove(index - 1);
		if (tasks.size() == 1) {
			ui.showLine("Now you have 1 task in the list.");
		} else {
			ui.showLine("Now you have " + tasks.size() + " tasks in the list.");
		}
	}

	@Override
	public boolean isExit() {
		return false;
	}
}

/**
 * FindCommand which executes the procedure for searching for Task objects in the TaskList which match the
 * keyword given by the user and shows the user a list of matching Tasks or else informs the user that no
 * matching Task objects were found
 */
class FindCommand extends Command {
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

/**
 * AddCommand that deals with the adding of new Task objects to the TaskList
 */
class AddCommand extends Command {
	String description;
	String taskType;

	public AddCommand(String description, String taskType) {
		this.taskType = taskType;
		this.description = description;
	}

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws ParseException {
		switch (taskType) {
			case "todo":
				tasks.add(new ToDo(description));
				break;
			case "deadline":
				String[] dInfo = description.split(" /by ");
				SimpleDateFormat dFormat = new SimpleDateFormat("ddMMyyyy HHmm");
				Date by = dFormat.parse(dInfo[1]);
				tasks.add(new Deadline(dInfo[0], by));
				break;
			case "event":
				String[] eInfo = description.split(" /at ");
				SimpleDateFormat eFormat = new SimpleDateFormat("ddMMyyyy HHmm");
				Date at = eFormat.parse(eInfo[1]);
				tasks.add(new Event(eInfo[0], at));
				break;
		}
	}

	@Override
	public boolean isExit() {
		return false;
	}
}

/**
 * DoneCommand that deals with marking Tasks in the TaskList as done
 */
class DoneCommand extends Command {
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
	}

	@Override
	public boolean isExit() {
		return false;
	}
}