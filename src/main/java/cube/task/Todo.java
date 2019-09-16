/**
 * This is the Todo class that inherit from Task.
 *
 * @author tygq13
 */
package task;

public class Todo extends Task{

	/**
	 * Default constructor.
	 * Call another constructor with (null) as argument.
	 */
	public Todo() {
		this(null);
	}

	/**
	 * Constructor with one argument.
	 * Call another constructor with additional argument done=false.
	 *
	 * @param description the description of the task
	 */
	public Todo(String description) {
		this(false, description);
	}

	/**
	 * Constructor with two argument.
	 *
	 * @param done indicate whether the task has been completed.
	 * @param description the description of the task.
	 */
	public Todo(boolean done, String description) {
		super(description);
		this.isDone = done;
	}

	/**
	 * Casts the task to String type.
	 *
	 * @return the String printout of the task
	 */
	@Override
	public String toString() {
		return "[T]" + super.toString();
	}

}