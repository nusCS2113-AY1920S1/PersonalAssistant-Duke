package exception;

public class WrongSearchSynonymFormatException extends WrongFormatException {
    /**
     * Creates an exception to inform if user inputs search_syn command wrongly.
     */
    public WrongSearchSynonymFormatException() {
        super(" OOPS:"
                + "  Expected format \"search_syn w/WORD\"");
    }
}
