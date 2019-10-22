package exception;

public class WrongEditFormatException extends WrongFormatException {
    public WrongEditFormatException() {
        super(" OOPS: Expected format \"edit w/WORD m/MEANING \"\n");
    }
}
