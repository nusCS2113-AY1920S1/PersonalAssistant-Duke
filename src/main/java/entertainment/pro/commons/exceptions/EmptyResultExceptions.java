package entertainment.pro.commons.exceptions;

import entertainment.pro.commons.strings.PromptMessages;

/**
 * Exceptions when there is results that match user's inputs.
 */
public class EmptyResultExceptions extends Exceptions {
    public EmptyResultExceptions() {
        super(PromptMessages.NO_RESULTS_FOUND);
    }
}
