package dolla.exception;

/**
 * This Exception class is used to handle limit related exceptions to give the user a better understanding of why
 * the program crashed.
 */
 //@author Weng-Kexin
public class LimitException extends Exception {
    
    public LimitException(String message) {
        super(message);
    }
}
