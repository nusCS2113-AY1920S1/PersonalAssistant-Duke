package exception;

public class WrongAddSynonymException extends WrongFormatException {
    public WrongAddSynonymException() {
        super(" OOPS: Expected format \"add w/WORD s/SYNONYMWORD\"\n");
    }
}
