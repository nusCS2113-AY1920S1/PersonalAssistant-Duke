package duke.commons.exceptions;

import duke.commons.Messages;

public class RecommendationDayExceededException extends DukeException {
    public RecommendationDayExceededException() {
        super(Messages.RECOMMENDATION_DAY_EXCEEDED);
    }
}
