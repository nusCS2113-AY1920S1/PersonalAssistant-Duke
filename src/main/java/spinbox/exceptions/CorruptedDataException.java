package spinbox.exceptions;

public class CorruptedDataException extends StorageException {
    private static final String ERROR_MESSAGE = "The data within your files have been corrupted. Pleas consider"
            +  " modifying them or deleting the folder and starting over.";

    public CorruptedDataException() {
        super(ERROR_MESSAGE);
    }
}
