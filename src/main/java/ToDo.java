/**
 * Subclass of Task
 * For any task that is not urgent enough to warrant a deadline or any events that are upcoming
 */
public class ToDo extends Task {
	/**
	 * Constructor of ToDo
	 * Works similarly as superclass Task
	 * @param description the description of the ToDo
	 */
	public ToDo(String description) {
		super(description);
		this.type = "[T]";
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
