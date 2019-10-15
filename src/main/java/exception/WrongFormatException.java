package exception;

public abstract class WrongFormatException extends WordUpException {
    public WrongFormatException(String message) {
        super(message + "\n You can type \"help\" to check the correct format");
    }
}
