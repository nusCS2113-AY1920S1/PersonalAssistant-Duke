package exception;

public class WrongSearchSynonymFormatException extends WrongFormatException {
    /**
     * Creates an exception to inform if user inputs search_tag command wrongly.
     */
    public WrongSearchSynonymFormatException() {
        super(" OOPS: Your input format is not correct for search_syn:\n"
                + "  Expected format \"search_synonym w/WORD\"");
    }
}
