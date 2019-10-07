package duke.model.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException() {
        super("Operation would result in duplicate entities");
    }
}