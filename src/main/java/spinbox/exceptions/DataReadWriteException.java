package spinbox.exceptions;

public class DataReadWriteException extends StorageException {
    private static final String ERROR_MESSAGE = "Error writing to/reading from files. Please ensure that they are "
            + "not currently opened in other text editors. You may also wish to delete past data (the entire folder) "
            + "and start over.";

    public DataReadWriteException() {
        super(ERROR_MESSAGE);
    }
}
