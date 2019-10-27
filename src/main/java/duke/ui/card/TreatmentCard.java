package duke.ui.card;

import duke.data.Treatment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * A UI card that displays the basic information of a {@code Treatment}.
 */
public abstract class TreatmentCard extends UiCard {
    private final String[] priorities = {"No priority", "Critical", "Urgent", "Compulsory", "Optional"};

    @FXML
    protected Label nameLabel;
    @FXML
    private Label criticalLabel;
    @FXML
    protected Label statusLabel;

    private final Treatment treatment;

    /**
     * Constructs a Treatment object with the specified {@code Treatment}'s details.
     *
     * @param fxmlFileName Name of FXML file.
     * @param treatment    Treatment object.
     * @param index        Displayed index.
     */
    public TreatmentCard(String fxmlFileName, Treatment treatment, int index) {
        super(fxmlFileName, index);

        this.treatment = treatment;
        fillTreatmentCard();
    }

    /**
     * Fills up the UI card with the {@code Treatment}'s details.
     */
    private void fillTreatmentCard() {
        String priorityText = String.valueOf(treatment.getPriority());
        if (treatment.getPriority() >= 0 && treatment.getPriority() < priorities.length) {
            priorityText += " - " + priorities[treatment.getPriority()];
        }
        criticalLabel.setText(priorityText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            return true;
        }

        return (object instanceof TreatmentCard);
    }

    public abstract Treatment getTreatment();
}
