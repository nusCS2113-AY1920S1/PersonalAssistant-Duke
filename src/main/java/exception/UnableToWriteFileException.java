package exception;

public class UnableToWriteFileException extends WordUpException {
    public UnableToWriteFileException()  {
        super("OOPS: Internal error occurred. Unable to write to file.");
    }
}
