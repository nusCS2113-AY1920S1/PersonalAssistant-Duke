package duke.exception;

import duke.*;
/**
 * Represents all exceptions unique to the execution of the duke.task.Task Manager program - Duke
 */
public class DukeException extends Exception {
	public DukeException(String message) {
		super(message);
	}
}