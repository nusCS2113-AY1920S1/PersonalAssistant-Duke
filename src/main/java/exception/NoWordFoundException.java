package exception;

public class NoWordFoundException extends WordUpException {
    private String searchWord;

    public NoWordFoundException(String searchWord) {
        super(" OOPS: I cannot find the your word: ");
        this.searchWord = searchWord;
    }

    @Override
    public String showError() {
        return (this.getMessage() + "\"" + searchWord + "\" \n");
    }
}