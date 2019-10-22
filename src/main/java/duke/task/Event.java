package duke.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Subclass of duke.task.Task
 * Describes specific type of task which can be classified as an event
 */
public class Event extends Task {
	private LocalDateTime datetime;

	/**
	 * Constructor for duke.task.Event
	 * Takes in a String description like superclass duke.task.Task
	 * Takes in another parameter Date to store when the duke.task.Event should be held at
	 * Allows reminding of user for when the duke.task.Event is going to be
	 * @param description the description of the event
	 * @param at the date and time at which the event will be held
	 */
	public Event(String description, Optional<String> filter, LocalDateTime at, String recurrencePeriod) {
		super(description, filter, recurrencePeriod);
		this.datetime = at;
		this.key = "[E]";
	}

	public Event(String description, Optional<String> filter, LocalDateTime at) {
		this(description, filter, at, "none");
	}

	public LocalDateTime getDateTime() {
		return this.datetime;
	}

	@Override
	public String toString() {
		return super.toString() + " (at: " + datetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ")";
	}
}
