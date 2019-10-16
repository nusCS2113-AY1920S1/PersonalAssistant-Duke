package exception;

public class InvalidAnswerException extends WordUpException {
    public InvalidAnswerException() {
        super("This is not a valid answer.\n" +
                "Please type a number from 1 to 4");
    }
}
