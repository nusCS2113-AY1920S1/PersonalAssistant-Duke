/**
 * This is the Deadline class that inherit from Task.
 *
 * @author tygq13
 */
package task;

import java.util.Date;
import util.Parser;

public class Deadline extends Task {
	private Date by;

	/**
	 * Constructor with no argument.
	 * Calls another constructor with (null, null) as arguments.
	 */
	public Deadline() {
		this(null, null);
	}

	/**
	 * Constructor with two arguments.
	 * Calls another constructor with additional argument isDone=false.
	 *
	 * @param description the description of the task.
	 * @param by the deadline time of the task.
	 */
	public Deadline(String description, Date by) {
		this(false, description, by);
	}

	/**
	 * Constructor with three arguments.
	 *
	 * @param done true if the task is marked as done, otherwise false.
	 * @param description the description of the task.
	 * @param by the deadline time of the task
	 */
	public Deadline(boolean done, String description, Date by) {
		super(description);
		this.by = by;
		this.isDone = done;
	}
	//todo: support pure string version like 'today' 'tomorrow'

	/**
	 * Casts the task to String type.
	 *
	 * @return the String printout of the task
	 */
	@Override
	public String toString() {
		return "[D]" + super.toString() + " (by: " + Parser.parseDateToString(by) + ")";
	}

	/**
	 * Breaks the task into a String array
	 *
	 * @return a String array of all components of the task
	 */
	@Override
	public String[] getTask() {
		return new String[] {description, Parser.parseDateToString(by)};
	}
}