package task;

import java.util.Date;
import util.Parser;

public class Deadline extends Task {
	private Date by;

	public Deadline() {
		this(null, null);
	}

	public Deadline(String task, Date by) {
		this(false, task, by);
	}

	public Deadline(boolean done, String task, Date by) {
		super(task);
		this.by = by;
		this.isDone = done;
		this.type = 'D';
	}
	//todo: support pure string version like 'today' 'tomorrow'

	@Override
	public String toString() {
		return "[D]" + super.toString() + " (by: " + Parser.parseDateToString(by) + ")";
	}

	@Override
	public String[] getTask() {
		return new String[] {task, Parser.parseDateToString(by)};
	}
}