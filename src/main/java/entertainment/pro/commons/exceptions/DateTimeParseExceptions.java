package entertainment.pro.commons.exceptions;

import entertainment.pro.commons.PromptMessages;

public class DateTimeParseExceptions extends Exceptions {
    public DateTimeParseExceptions() {
        super(PromptMessages.INVALID_FORMAT);
    }
}
