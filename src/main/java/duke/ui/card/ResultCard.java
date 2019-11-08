package duke.ui.card;

import duke.data.Evidence;
import duke.data.Result;
import duke.exception.DukeFatalException;

/**
 * A UI card that displays the basic information of a {@code Result}.
 */
public class ResultCard extends EvidenceCard {
    private static final String FXML = "ResultCard.fxml";

    private final Result result;

    /**
     * Constructs a ResultCard object with the specified {@code Result}'s details.
     *
     * @param result Result object.
     */
    public ResultCard(Result result) throws DukeFatalException {
        super(FXML, result);

        this.result = result;
    }

    @Override
    public Evidence getEvidence() {
        return result;
    }
}
