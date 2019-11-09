package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when the Recommendations fails.
 */
public class RecommendationFailException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public RecommendationFailException() {
        super(Messages.ERROR_RECOMMENDATION_FAIL);
    }
}
