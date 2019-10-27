package exception;

public class WrongSearchBeginFormatException extends WrongFormatException {
    public WrongSearchBeginFormatException() {
        super(" OOPS: Expected format \"search_begin w/WORD_TO_BE_SEARCHED\"\n");
    }
}
