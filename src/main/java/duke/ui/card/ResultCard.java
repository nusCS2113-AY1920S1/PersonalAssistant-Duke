package duke.ui.card;

import duke.data.Result;

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
    public ResultCard(Result result) {
        super(FXML, result);

        this.result = result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            return true;
        }

        if (!(object instanceof ResultCard)) {
            return false;
        }

        ResultCard card = (ResultCard) object;
        return result.equals(card.getResult());
    }

    public Result getResult() {
        return result;
    }
}
