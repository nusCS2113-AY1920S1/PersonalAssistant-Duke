package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

public class RecommendationFailException extends DukeException {
    public RecommendationFailException() {
        super(Messages.ERROR_RECOMMENDATION_FAIL);
    }
}
