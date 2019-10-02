package duke.task;

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
	public FixedDurationTask(String description, int duration) {
		super(description);
		this.duration = duration;
		this.type = "[F]";
	}

	@Override
	public String toString() {
		return super.toString() + " (needs " + duration + " hours)";
	}
}
