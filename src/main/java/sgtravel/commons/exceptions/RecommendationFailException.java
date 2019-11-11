package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Displays an error when the trip is too long (greater than 8 days).
 */
public class RecommendationFailException extends SingaporeTravelException {

    /**
     * Constructs the Exception.
     */
    public RecommendationFailException() {
        super(Messages.ERROR_RECOMMENDATION_FAIL);
    }
}
