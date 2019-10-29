package exception;

public class WordCountEmptyException extends WordUpException {
    public WordCountEmptyException() {
        super(" OOPS: Word count is empty. Please input a word before viewing list.");
    }
}
