package exception;

public class InvalidInputWordException extends WordUpException {
    public InvalidInputWordException() {
        super("OOPS!!! Your input word contain invalid character\n Input word should have letters a-z or A-Z only");
    }
}
