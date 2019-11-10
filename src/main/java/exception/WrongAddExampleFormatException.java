package exception;

public class WrongAddExampleFormatException extends WordUpException {
    public WrongAddExampleFormatException() {
        super(" OOPS: Expected format \"add_example w/WORD e/EXAMPLE\"\n");
    }
}