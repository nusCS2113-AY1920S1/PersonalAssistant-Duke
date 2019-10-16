package exception;

public class NoWordFoundException extends WordUpException {
    private String searchWord;

    public NoWordFoundException(String searchWord) {
        super(" OOPS: I cannot find the meaning of your word: ");
        this.searchWord = searchWord;
    }

    @Override
    public String showError() {
        return (this.getMessage() + "\"" + searchWord + "\"");
    }
}