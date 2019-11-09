package diyeats.commons.exceptions;

/**
 * ProgramException is an exception class that extends from the class Exception.
 */
public class ProgramException extends Exception {
    /**
     * This is the constructor of ProgramException.
     * @param messageStr this is the error message generated from a ProgramException.
     */
    public ProgramException(String messageStr) {
        super(messageStr);
    }
}