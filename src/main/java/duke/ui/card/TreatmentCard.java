package duke.ui.card;

import duke.data.Treatment;
import duke.exception.DukeFatalException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

//@@author JeremyKwok
/**
 * A UI card that displays the basic information of a {@code Treatment}.
 */
public abstract class TreatmentCard extends UiCard {
    @FXML
    protected Label nameLabel;
    @FXML
    protected Label statusLabel;
    @FXML
    private Label criticalLabel;

    private final Treatment treatment;

    /**
     * Constructs a Treatment object with the specified {@code Treatment}'s details.
     *
     * @param fxmlFileName Name of FXML file.
     * @param treatment    Treatment object.
     */
    TreatmentCard(String fxmlFileName, Treatment treatment) throws DukeFatalException {
        super(fxmlFileName);

        this.treatment = treatment;
        fillTreatmentCard();
    }

    /**
     * Fills up the UI card with the {@code Treatment}'s details.
     */
    private void fillTreatmentCard() {
        nameLabel.setText(treatment.getName());
        criticalLabel.setText(treatment.getPriorityStr());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            return true;
        }

        if (!(object instanceof TreatmentCard)) {
            return false;
        }

        TreatmentCard card = (TreatmentCard) object;
        return treatment == card.getTreatment();
    }

    public abstract Treatment getTreatment();
}
