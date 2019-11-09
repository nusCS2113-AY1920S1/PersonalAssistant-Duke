package entertainment.pro.commons.exceptions;

import entertainment.pro.commons.PromptMessages;

/**
 * Exception thrown when the command enterted by user is invalid.
 */
public class InvalidFormatCommandException extends Exceptions {
    public InvalidFormatCommandException(String message) {
        super(message);
    }

    public InvalidFormatCommandException() {
        super(PromptMessages.INVALID_FORMAT);

    }
}
