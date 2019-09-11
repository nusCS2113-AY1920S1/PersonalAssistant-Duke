/**
 * This is the Task class that used as parent class for all types of tasks.
 *
 * @author tygq13
 */
package task;

import java.io.Serializable;

public class Task implements Serializable{
	protected String description;
	protected boolean isDone;

	/**
	 * Default constructor.
	 * Calls another constructor with (null) as argument.
	 */
	public Task() {
		this(null);
	}

	/**
	 * Constructor with one argument.
	 * Sets isDone to false by default.
	 *
	 * @param description the description of the task.
	 */
	public Task(String description) {
		this.description = description;
		this.isDone = false;
	}

	/**
	 * Casts the task to String type.
	 *
	 * @return the String printout of the task
	 */
	public String[] getTask() {
		return new String[] {description};
	}

	/**
	 * Gets the status of the task.
	 *
	 * @return true if the task is done, otherwise false.
	 */
	public boolean getStatus() {
		return isDone;
	}

	/**
	 * Returns icon 'Tick" if task is done, icon 'Cross' if it is not done.
	 *
	 * @return the icon of the status
	 */
	public String getStatusIcon() {
		return (isDone ? "\u2713" : "\u2718"); //tick or cross
	}

	/**
	 * Casts the task to String type.
	 *
	 * @return the String printout of the event.
	 */
	@Override
	public String toString() {
		return "[" + getStatusIcon() + "] " + description; 
	}

	/**
	 *  Sets isDone to true.
	 */
	public void markAsDone() {
		this.isDone = true;
	}
}