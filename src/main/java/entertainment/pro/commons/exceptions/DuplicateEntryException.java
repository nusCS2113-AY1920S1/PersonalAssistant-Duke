package entertainment.pro.commons.exceptions;

/**
 * Exception for duplicate entry.
 */
public class DuplicateEntryException extends Exception {
    public DuplicateEntryException(String source) {
        super("Duplicate Entry in " + source);
    }
}
