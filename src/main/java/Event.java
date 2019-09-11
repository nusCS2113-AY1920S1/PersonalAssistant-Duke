import java.util.Date;

/**
 * Subclass of Task
 * Describes specific type of task which can be classified as an event
 */
public class Event extends Task {
	private Date datetime;

	/**
	 * Constructor for Event
	 * Takes in a String description like superclass Task
	 * Takes in another parameter Date to store when the Event should be held at
	 * Allows reminding of user for when the Event is going to be
	 * @param description the description of the event
	 * @param at the date and time at which the event will be held
	 */
	public Event(String description, Date at) {
		super(description);
		this.datetime = at;
		this.type = "[E]";
	}

	@Override
	public String toString() {
		return super.toString() + " (at: " + datetime + ")";
	}
}
