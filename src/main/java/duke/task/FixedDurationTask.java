package duke.task;

import java.util.Optional;

/**
 * Subclass of duke.task.Task
 * Describes a task which has a fixed duration to complete
 */
public class FixedDurationTask extends Task {
	private Integer duration;

	/**
	 * Constructor of FixedDuration
	 * Creates an instance of a FixedDurationTask which will be able to
	 * display to user what the task is and how long it take to complete
	 * @param description the description of the task
	 * @param duration how long the task needs to complete in hours
	 */
	public FixedDurationTask(String description, Optional<String> filter, int duration) {
		super(description, filter);
		this.duration = duration;
		this.key = "[F]";
	}

	@Override
	public String toString() {
		return super.toString() + " (needs " + duration + " hours)";
	}
}
