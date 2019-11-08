package diyeats.commons.exceptions;

/**
 * DukeException is an exception class that extends from the class Exception.
 */
public class ProgramException extends Exception {
    /**
     * This is the constructor of DukeException.
     * @param messageStr this is the error message generated from a DukeException
     */
    public ProgramException(String messageStr) {
        super(messageStr);
    }
}