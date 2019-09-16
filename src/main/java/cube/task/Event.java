/**
 * This is the Event class that inherit from Task.
 *
 * @author tygq13
 */
package task;

import java.util.Date;
import util.Parser;

public class Event extends Task{
	private Date at;

	/**
	 * Default constructor with no argument.
	 * Calls another constructor with (null, null) as arguments.
	 */
	public Event() {
		this(null, null);
	}

	/**
	 * Constructor with two arguments.
	 * Calls another constructor with additional parameter done=false.
	 *
	 * @param description the descirption of the event.
	 * @param at the happening time of the event.
	 */
	public Event(String description, Date at) {
		this(false, description, at);
	}

	/**
	 * Constructor with three arguments.
	 *
	 * @param done indicate whether the event has completed.
	 * @param description the description of the event.
	 * @param at the happening time of the event.
	 */
	public Event(boolean done, String description, Date at) {
		super(description);
		this.at = at;
		this.isDone = done;
	}

	/**
	 * Casts the task to String type.
	 *
	 * @return the String printout of the event.
	 */
	@Override
	public String toString() {
		return "[E]" + super.toString() + " (at: " + Parser.parseDateToString(at) + ")";
	}

	/**
	 * Breaks the task into a String array
	 *
	 * @return a String array of all components of the event
	 */
	@Override
	public String[] getTask() {
		return new String[] {description, Parser.parseDateToString(at)};
	}
}