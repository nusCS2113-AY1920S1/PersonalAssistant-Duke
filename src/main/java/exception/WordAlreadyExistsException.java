package exception;

public class WordAlreadyExistsException extends WordUpException {
    public WordAlreadyExistsException(String word) {
        super(" OOPS: This word has already existed in the bank\n" + word);
    }
}
