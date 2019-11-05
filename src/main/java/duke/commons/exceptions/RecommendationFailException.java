package duke.commons.exceptions;

import duke.commons.Messages;

public class RecommendationFailException extends DukeException {
    public RecommendationFailException() {
        super(Messages.ERROR_RECOMMENDATION_FAIL);
    }
}
