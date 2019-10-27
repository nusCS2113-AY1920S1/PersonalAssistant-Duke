package entertainment.pro.commons.exceptions;

import entertainment.pro.commons.PromptMessages;

/**
 * Exception thrown when reauest sent to the API is null;
 */
public class FailedAPINullRequest extends Exceptions {
    public FailedAPINullRequest(String message) {
        super(PromptMessages.API_INVALID_REQUEST);
    }
}
