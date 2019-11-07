
package gazeeebo.exception;


public class DukeException extends Exception {
    /**This method is a custom Exception that would throw our custom error message.
     *
     * @param errorMessage String of error message that will display in console
     */
    public DukeException(final String errorMessage) {
        super(errorMessage);
    }
}

