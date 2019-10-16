package spinbox.exceptions;

public class StorageException extends SpinBoxException {
    public StorageException(String errMsg) {
        super("Storage Error\n\n" + errMsg);
    }
}
