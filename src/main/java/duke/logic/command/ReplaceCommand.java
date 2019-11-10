package duke.logic.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * duke.logic.parser.command.ReplaceCommand which is the mirror task to undo duke.logic.parser.command.ClearCommand
 * action of clearing duke.task.Task objects of a certain filter from the duke.tasklist.TaskList
 */
public class ReplaceCommand extends Command {
	private ArrayList<Task> taskList;
	private Optional<String> filter;

	/**
	 * Constructor for ReplaceCommand
	 * Creates a copy of the current state of the TaskList before ClearCommand is executed on it.
	 * @param tasks list of tasks in current task list
	 * @param filter filter of tasks to be cleared from the task list
	 */
	public ReplaceCommand(ArrayList<Task> tasks, Optional<String> filter) {
		this.taskList = new ArrayList<>(tasks);
		this.filter = filter;
	}

	/**
	 * Replaces the current task list with the task list before clear was executed on it, restoring it to its
	 * previous state
	 *
	 * @param tasks TaskList of all the user tasks
	 * @param ui Ui handling user interactions
	 * @param storage Storage handling saving and loading of the TaskList
	 * @throws IOException if error occurs saving data to the JSON file
	 * @throws ParseException NA
	 * @throws DukeException NA
	 */
	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, ParseException, DukeException {
		tasks.replace(taskList);
		if (filter.isPresent()) {
			ui.showLine(filter.get() + " clear command has been undone!");
		} else {
			ui.showLine("Clear command has been undone!");
		}
		storage.save(tasks);
	}

	@Override
	public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {
	}
}
