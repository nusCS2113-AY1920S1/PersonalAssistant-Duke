package duke.task;

import java.util.Optional;

/**
 * Subclass of duke.task.Task
 * For any task that is not urgent enough to warrant a deadline or any events that are upcoming
 */
public class ToDo extends Task {
	/**
	 * Constructor of duke.task.ToDo
	 * Works similarly as superclass duke.task.Task
	 * Constructs a task which has no fixed duration to complete
	 * @param description the description of the duke.task.ToDo
	 */
	public ToDo(String description, Optional<String> filter, String recurrencePeriod) {
		super(description, filter, recurrencePeriod);
		this.key = "[T]";
	}

	public ToDo(String description, Optional<String> filter) {
		this(description, filter, "none");
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
