package exception;

public class NoWordFoundException extends DukeException {
    private String searchWord;

    public NoWordFoundException(String searchWord) {
        super("     ☹ OOPS: The word you are searching is not in the word bank: ");
        this.searchWord = searchWord;
    }

    @Override
    public String showError() {
        return this.getMessage() + searchWord;
    }
}
