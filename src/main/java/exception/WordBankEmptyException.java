package exception;

public class WordBankEmptyException extends WordUpException {
    public WordBankEmptyException() {
        super("     ☹ OOPS: Your word bank is empty. Please input a word before\n       viewing list.");
    }
}
