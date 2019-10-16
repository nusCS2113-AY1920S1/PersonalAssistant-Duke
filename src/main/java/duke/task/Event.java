package duke.task;

import java.time.LocalDate;

/**
 * Subclass of duke.task.Task
 * Describes specific type of task which can be classified as an event
 */
public class Event extends Task {
	private LocalDate datetime;

	/**
	 * Constructor for duke.task.Event
	 * Takes in a String description like superclass duke.task.Task
	 * Takes in another parameter Date to store when the duke.task.Event should be held at
	 * Allows reminding of user for when the duke.task.Event is going to be
	 * @param description the description of the event
	 * @param at the date and time at which the event will be held
	 */
	public Event(String description, LocalDate at, String recurrencePeriod) {
		super(description, recurrencePeriod);
		this.datetime = at;
		this.key = "[E]";
	}

	public Event(String description, LocalDate at) {
		this(description, at, "none");
	}

	public LocalDate getDatetime() {
		return this.datetime;
	}

	@Override
	public String toString() {
		return super.toString() + " (at: " + datetime + ")";
	}
}
