package spinbox.exceptions;

public class CorruptedDataException extends StorageException {
    public CorruptedDataException(String errMsg) {
        super(errMsg);
    }
}
