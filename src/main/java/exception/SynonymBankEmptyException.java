package exception;

public class SynonymBankEmptyException extends WordUpException {
    public SynonymBankEmptyException() {
        super(" OOPS: Your synonym bank is empty. Please fill in some synonyms to your words before viewing it.");
    }
}
