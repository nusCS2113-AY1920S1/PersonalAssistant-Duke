package entertainment.pro.commons.exceptions;

import entertainment.pro.commons.PromptMessages;

/**
 * Exception thrown when parse exception detected when extracting data.
 */
public class ParseExceptionInExtraction extends Exceptions{
    public ParseExceptionInExtraction() {
        super(PromptMessages.PARSE_EXCEPTION_IN_EXTRACTION);
    }
}
