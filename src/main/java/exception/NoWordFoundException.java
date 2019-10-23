package exception;

public class NoWordFoundException extends WordUpException {
    private String searchWord;

    public NoWordFoundException(String searchWord) {
        super(" OOPS: I could not find your word: ");
        this.searchWord = searchWord;
    }

    @Override
    public String showError() {
        return (this.getMessage() + "\"" + searchWord + "\"");
    }
}