package exception;

public class WrongDeleteFormatException extends WrongFormatException {
    public WrongDeleteFormatException() {
        super(" OOPS: Your input format is incorrect" + "\n" +
                "If you want to delete tag(s):\n    Expected format \"delete w/WORD_TO_BE_DELETED [t/TAG]...\"" + "\n" +
                "If you want to delete word:\n      Expected format \"delete w/WORD_TO_BE_DELETED\"\n");
    }
}