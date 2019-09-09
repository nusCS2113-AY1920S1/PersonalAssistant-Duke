package command;

import java.util.Date;

import ui.*;
import util.Storage;
import task.TaskList;
import task.Deadline;
import exception.DukeException;

public class DeadlineCommand implements Command{
	private String description;
	private Date date;

	public DeadlineCommand() {
		this(null);
	}

	public DeadlineCommand(String description) {
		this(description, null);
	}

	public DeadlineCommand(String description, Date date) {
		this.description = description;
		this.date = date;
	}

	private boolean isValid() throws DukeException{
		if (description == null) {
			throw new DukeException(Message.EMPTY_DESCRIPTION);
		}
		if (date == null) {
			throw new DukeException(Message.EMPTY_DATE);
		}
		return true;
	}

	@Override
	public boolean isExit() {
		return false;
	}

	@Override
	public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
		if (isValid()) {
			Deadline d = new Deadline(description, date);
			tasks.add(d);
			storage.append(d);
			ui.showAdd(tasks);
		}
	}
}