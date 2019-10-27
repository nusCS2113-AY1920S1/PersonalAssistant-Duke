package exception;

public class WordAlreadyExistException extends WordUpException {
    public WordAlreadyExistException(String word) {
        super(" OOPS: This word has already exists in the bank\n" + word);
    }
}
