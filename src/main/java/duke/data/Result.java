package duke.data;

import duke.exception.DukeException;
import duke.exception.DukeFatalException;
import duke.ui.card.ResultCard;
import duke.ui.context.Context;

public class Result extends Evidence {

    /**
     * Represents results of an investigation based on the treatment prescribed for a patient.
     * A Result object corresponds to the result of an investigation into the symptoms of a Patient,
     * the particular impression, as well as an integer between 1-4 representing the priority
     * or significance of the result.
     * Attributes:
     * @param name the result
     * @param impression the impression object the result is tagged to
     * @param summary a summary of the result
     * @param priority the priority level of the evidence
    */
    public Result(String name, Impression impression, int priority, String summary) throws DukeException {
        super(name, impression, priority, summary);
    }

    @Override
    public String toReportString() {
        // todo
        return null;
    }

    public ResultCard toCard() throws DukeFatalException {
        return new ResultCard(this);
    }

    @Override
    public Context toContext() {
        return Context.EVIDENCE;
    }
}
