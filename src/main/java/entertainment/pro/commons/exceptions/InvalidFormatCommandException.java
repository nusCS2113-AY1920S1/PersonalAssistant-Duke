package entertainment.pro.commons.exceptions;

import entertainment.pro.commons.PromptMessages;

public class InvalidFormatCommandException extends Exceptions {
    public InvalidFormatCommandException(String message) {
        super(message);
    }

    public InvalidFormatCommandException() {
        super(PromptMessages.INVALID_FORMAT);

    }
}
