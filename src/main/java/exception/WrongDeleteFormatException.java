package exception;

public class WrongDeleteFormatException extends WrongFormatException {
    public WrongDeleteFormatException() {
        super("     ☹ OOPS: Your input format is incorrect" +
                "     If you want to delete tag(s):\n     Expected format \"delete w/WORD_TO_BE_DELETED [t/TAG]...\"" +
                "     If you want to delete word:\n     Expected format \"delete w/WORD_TO_BE_DELETED\"");
    }
}