package spinbox.exceptions;

public class StorageException extends SpinBoxException {
    public StorageException(String errMsg) {
        super("I/O Error\n\n" + errMsg);
    }
}
