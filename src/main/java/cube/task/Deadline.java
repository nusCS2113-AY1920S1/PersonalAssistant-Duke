/**
 * This is the Deadline class that inherit from Task.
 *
 * @author tygq13
 */
package cube.task;

import cube.util.Parser;

import java.util.Date;

public class Deadline extends Task {

	/**
	 * Constructor with no argument.
	 * Calls another constructor with (null, null) as arguments.
	 */
	public Deadline() {
		this(null, null);
	}

	/**
	 * Constructor with two arguments.
	 * Calls another constructor with additional parameter isDone=false.
	 *
	 * @param description the description of the task.
	 * @param date the date of the deadline.
	 */
	public Deadline(String description, Date date) {
		this(false, description, date);
	}

	/**
	 * Constructor with threee arguments.
	 *
	 * @param done true if the task is marked as done, otherwise false.
	 * @param description the description of the task.
	 * @param date the date of the deadline.
	 */
	public Deadline(boolean done, String description, Date date) {
		super(description, date);
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
		return "[D]" + super.toString() + " (by: " + Parser.parseDateToString(date) + ")";
	}
}