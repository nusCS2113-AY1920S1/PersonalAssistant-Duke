package command;

import java.util.Date;

import ui.*;
import util.Storage;
import task.TaskList;
import task.Event;
import exception.DukeException;

public class EventCommand implements Command{
	private String description;
	private Date date;

	public EventCommand() {
		this(null);
	}

	public EventCommand(String description) {
		this(description, null);
	}

	public EventCommand(String description, Date date) {
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
	public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException{
		if (isValid()) {
			Event e = new Event(description, date);
			tasks.add(e);
			storage.append(e);
			ui.showAdd(tasks);
		}
	}
}