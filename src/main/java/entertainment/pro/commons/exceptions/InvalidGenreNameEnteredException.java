package entertainment.pro.commons.exceptions;

import entertainment.pro.commons.strings.PromptMessages;

/**
 * Exception thrown when user enters a invalid genre name.
 */
public class InvalidGenreNameEnteredException extends Exceptions {
    public InvalidGenreNameEnteredException() {
        super(PromptMessages.INVALID_GENRE_NAME);
    }
}
