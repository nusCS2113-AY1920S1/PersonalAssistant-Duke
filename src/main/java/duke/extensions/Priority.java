package duke.extensions;

import duke.exception.DukeException;

/**
 * Enum priority that allows labelling of tasks by priority levels
 */
public enum Priority {
	LOW, MEDIUM, HIGH;

	/**
	 * Method to get priority level of a task
	 * @return integer value representing the priority level of the task
	 * @throws DukeException
	 */
	public int priorityLevel() throws DukeException {
		switch(this) {
			case LOW:
				return 0;
			case MEDIUM:
				return 1;
			case HIGH:
				return 2;
			default:
				throw new DukeException("No such priority exists.");
		}
	}
}
