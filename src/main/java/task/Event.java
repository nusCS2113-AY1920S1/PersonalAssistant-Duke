package task;

import java.util.Date;
import util.Parser;

public class Event extends Task{
	private Date at;

	public Event() {
		this(null, null);
	}

	public Event(String task, Date at) {
		this(false, task, at);
	}

	public Event(boolean done, String task, Date at) {
		super(task);
		this.at = at;
		this.isDone = done;
		this.type = 'E';
	}

	@Override
	public String toString() {
		return "[E]" + super.toString() + " (at: " + Parser.parseDateToString(at) + ")";
	}

	@Override
	public String[] getTask() {
		return new String[] {task, Parser.parseDateToString(at)};
	}
}