package exception;

public class WrongAddExampleFormatException extends WordUpException {
    public WrongAddExampleFormatException() {
        super(" OOPS: Expected format \"add w/WORD e/EXAMPLE\"\n");
    }
}