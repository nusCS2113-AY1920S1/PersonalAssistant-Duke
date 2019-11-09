package entertainment.pro.commons.exceptions;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.exceptions.Exceptions;

public class EmptyResultExceptions extends Exceptions {
    public EmptyResultExceptions() {
        super(PromptMessages.NO_RESULTS_FOUND);
    }
}
