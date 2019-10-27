package exception;

public class WordAlreadyExistsException extends WordUpException {
    public WordAlreadyExistsException(String word) {
        super(" OOPS: This word has already exists in the bank\n" + word);
    }
}
