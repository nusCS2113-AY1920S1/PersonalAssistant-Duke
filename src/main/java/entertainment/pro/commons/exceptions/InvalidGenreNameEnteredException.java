package entertainment.pro.commons.exceptions;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.ui.MovieHandler;

public class InvalidGenreNameEnteredException extends Exceptions {
    public InvalidGenreNameEnteredException() {
        super(PromptMessages.INVALID_GENRE_NAME);
    }
}
