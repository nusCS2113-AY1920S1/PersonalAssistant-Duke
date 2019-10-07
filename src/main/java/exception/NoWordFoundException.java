package exception;

public class NoWordFoundException extends WordUpException {
    private String searchWord;

    public NoWordFoundException(String searchWord) {
        super("     ☹ OOPS: The word you are searching is not in the word bank: ");
        this.searchWord = searchWord;
    }

    @Override
    public void showError() {
        System.out.println(this.getMessage() + searchWord);
    }
}
