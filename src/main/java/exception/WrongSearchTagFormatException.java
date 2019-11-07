package exception;

public class WrongSearchTagFormatException extends WrongFormatException {
    /**
     * Creates an exception to inform if user inputs search_tag command wrongly.
     */
    public WrongSearchTagFormatException() {
        super(" OOPS: Your input format is not correct for search_tag:\n"
                + "If you search for words of a tag:\n"
                + "  Expected format \"search_tag t/TAG\"\n"
                + "If you search for all tags of a word:\n"
                + "  Expected format \"search_tag w/WORD\"");
    }
}
