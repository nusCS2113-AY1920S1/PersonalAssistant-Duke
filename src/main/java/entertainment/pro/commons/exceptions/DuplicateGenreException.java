package entertainment.pro.commons.exceptions;

import entertainment.pro.commons.strings.PromptMessages;

/**
 * Responsible for throwing exception when user adds a genre that already exist in the database.
 */
public class DuplicateGenreException extends Exceptions {
    public DuplicateGenreException() {
        super(PromptMessages.REPETITVE_GENRE_NAME);
    }
}
