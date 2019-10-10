package duke.exceptions;

public class StorageException extends DukeException {
    public StorageException(String errMsg) {
        super("I/O Error\n\n" + errMsg);
    }
}
