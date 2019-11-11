package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when the UserGuide cannot be opened in a browser.
 */
public class HelpFailException extends SingaporeTravelException {

    /**
     * Constructs the Exception.
     */
    public HelpFailException() {
        super(Messages.ERROR_HELP_FAIL);
    }
}
