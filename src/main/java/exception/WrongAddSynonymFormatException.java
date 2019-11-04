package exception;

public class WrongAddSynonymFormatException extends WrongFormatException {
    public WrongAddSynonymFormatException() {
        super(" OOPS: Expected format \"addsyn w/WORD s/SYNONYMWORD\"\n");
    }
}
