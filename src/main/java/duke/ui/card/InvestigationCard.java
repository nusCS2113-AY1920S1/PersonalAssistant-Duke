package duke.ui.card;

import duke.data.Investigation;
import duke.data.Treatment;

/**
 * A UI card that displays the basic information of an {@code Investigation}.
 */
public class InvestigationCard extends TreatmentCard {
    private static final String FXML = "InvestigationCard.fxml";

    private final Investigation investigation;

    /**
     * Constructs an InvestigationCard object with the specified {@code Investigation}'s details.
     *
     * @param investigation Investigation object.
     */
    public InvestigationCard(Investigation investigation) {
        super(FXML, investigation);

        this.investigation = investigation;
        fillInvestigationCard();
    }

    /**
     * Fills up the UI card with the {@code Investigation}'s details.
     */
    private void fillInvestigationCard() {
        nameLabel.setText(investigation.getName());

        String statusText = String.valueOf(investigation.getStatusIdx());
        if (investigation.getStatusIdx() >= 0 && investigation.getStatusIdx() < investigation.getStatusArr().size()) {
            statusText += " - " + investigation.getStatusStr();
        }
        statusLabel.setText(statusText);
    }

    @Override
    public Treatment getTreatment() {
        return investigation;
    }
}
