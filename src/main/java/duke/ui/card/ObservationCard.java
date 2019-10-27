package duke.ui.card;

import duke.data.Evidence;
import duke.data.Observation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * A UI card that displays the basic information of an {@code Observation}.
 */
public class ObservationCard extends EvidenceCard {
    private static final String FXML = "ObservationCard.fxml";

    @FXML
    private Label objectiveLabel;

    private final Observation observation;

    /**
     * Constructs an ObservationCard object with the specified {@code Observation}'s details.
     *
     * @param observation Observation object.
     * @param index       Displayed index.
     */
    public ObservationCard(Observation observation, int index) {
        super(FXML, observation, index);

        this.observation = observation;
        fillObservationCard();
    }

    /**
     * Fills up the UI card with the {@code Observation}'s details.
     */
    private void fillObservationCard() {
        if (observation.isObjective()) {
            objectiveLabel.setText("Objective");
        } else {
            objectiveLabel.setText("Subjective");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            return true;
        }

        if (!(object instanceof ObservationCard)) {
            return false;
        }

        ObservationCard card = (ObservationCard) object;
        return observation.equals(card.getEvidence());
    }

    @Override
    public Evidence getEvidence() {
        return observation;
    }
}
