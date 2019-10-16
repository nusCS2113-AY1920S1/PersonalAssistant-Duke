/**
 * This is the Event class that inherit from Task.
 *
 * @author tygq13
 */
package cube.task;

import java.util.Date;

public class Event extends Task{

	/**
	 * Default constructor with no argument.
	 * Calls another constructor with (null, null) as arguments.
	 */
	public Event() {
		this(null, null);
	}

	/**
	 * Constructor with two arguments.
	 * Calls another constructor with additional parameter isDone=false.
	 *
	 * @param description the description of the task.
	 * @param date the date of the event.
	 */
	public Event(String description, Date date) {
		this(false, description, date);
	}

	/**
	 * Constructor with threee arguments.
	 *
	 * @param done true if the task is marked as done, otherwise false.
	 * @param description the description of the task.
	 * @param date the date of the event.
	 */
	public Event(boolean done, String description, Date date) {
		super(description, date);
		this.isDone = done;
	}

	/**
	 * Casts the task to String type.
	 *
	 * @return the String printout of the event.
	 */
	@Override
	public String toString() {
		return "[E]" + super.toString() + " (at: " + Parser.parseDateToString(date) + ")";
	}

}