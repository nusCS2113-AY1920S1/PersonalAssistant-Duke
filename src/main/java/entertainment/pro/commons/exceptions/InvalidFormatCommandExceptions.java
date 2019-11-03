package entertainment.pro.commons.exceptions;

import entertainment.pro.commons.PromptMessages;

public class InvalidFormatCommandExceptions extends Exceptions {
    public InvalidFormatCommandExceptions() {
        super(PromptMessages.INVALID_FORMAT);
    }
}
