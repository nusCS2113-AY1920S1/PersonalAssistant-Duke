package duke.task;

/**
 * Superclass for all Tasks that will be added to the duke.task.Task Manager
 */
public class Task {
	protected String description;
	protected boolean isDone;
	protected String type;

	/**
	 * Constructor function for duke.task.Task
	 * Creates a new instance of duke.task.Task by taking in a String description
	 * Automatically flags the boolean isDone as False
	 * Default Tasks have no type
	 * @param description the description of the task
	 */
	public Task(String description) {
		this.description = description;
		this.isDone = false;
		this.type = "";
	}

	/**
	 * Returns a String object to show if a duke.task.Task has been marked done or not
	 * @return tick if done or X symbol if not done
	 */
	public String getStatusIcon() {
		return (isDone ? "✓" : "✗");
	}

	/**
	 * Flags the boolean attribute isDone as true in a task
	 * Prints out the confirmation that the task is marked done
	 */
	public void markAsDone() {
		isDone = true;
		System.out.println("Nice! I've marked this task as done:");
		System.out.println(this);
	}

	/**
	 * Returns a String which describes the task
	 * @return the description of the task
	 */
	public String getDescription() {
		return this.description;
	}

	@Override
	public String toString() {
		return type + "[" + this.getStatusIcon() + "] " + description;
	}
}
