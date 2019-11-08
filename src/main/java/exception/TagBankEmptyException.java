package exception;

public class TagBankEmptyException extends WordUpException {
    public TagBankEmptyException() {
        super(" OOPS: Your tag bank is empty. Please add some tags to your words before viewing it.");
    }
}
