package exception;

public class InvalidCharacterException extends WordUpException {
    public InvalidCharacterException() {
        super(" OOPS: Your input contains non-alphabetical characters. "
                + "Please ensure that all parameters are unicode alphabets.");
    }
}

