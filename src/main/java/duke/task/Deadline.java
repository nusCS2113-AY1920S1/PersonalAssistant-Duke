package duke.task;

import java.util.Date;

/**
 * Subclass of duke.task.Task
 * Describes an event which is classified as a deadline
 */
public class Deadline extends Task {
	private Date datetime;

	/**
	 * Constructor of deadline
	 * Creates an instance of a deadline object by taking in its description
	 * and the date which it should be done by
	 * @param description the description of the deadline
	 * @param by the date and time which the deadline should be done by
	 */
	public Deadline(String description, Date by) {
		super(description);
		this.datetime = by;
		this.type = "[D]";
	}

	@Override
	public String toString() {
		return super.toString() + " (by: " + datetime + ")";
	}
}
