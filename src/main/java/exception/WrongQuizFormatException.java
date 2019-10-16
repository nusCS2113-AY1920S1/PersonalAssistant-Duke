package exception;

public class WrongQuizFormatException extends WrongFormatException {
    public WrongQuizFormatException() {
        super("Only \"quiz\" is enough for this command");
    }
}