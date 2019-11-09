package entertainment.pro.commons.exceptions;

import entertainment.pro.commons.PromptMessages;

/**
 * Responsible for throwing exception when user wanst to remove a genre that does not exist in the database.
 */
public class GenreDoesNotExistException extends Exceptions {
    public GenreDoesNotExistException() {
        super(PromptMessages.GENRE_DOES_NOT_EXIST);
    }
}
