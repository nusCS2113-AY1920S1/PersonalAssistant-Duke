package duke.ui.card;

import duke.data.Evidence;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * A UI card that displays the basic information of an {@code Evidence}.
 */
public abstract class EvidenceCard extends UiCard {
    private final String[] priorities = {"No priority", "Critical", "Urgent", "Compulsory", "Optional"};

    @FXML
    protected Label nameLabel;
    @FXML
    protected Label criticalLabel;

    private final Evidence evidence;

    /**
     * Constructs a EvidenceCard object with the specified {@code Evidence}'s details.
     *
     * @param fxmlFileName Name of FXML file.
     * @param evidence     Evidence object.
     */
    public EvidenceCard(String fxmlFileName, Evidence evidence) {
        super(fxmlFileName);

        this.evidence = evidence;
        fillEvidenceCard();
    }

    /**
     * Fills up the UI card with the {@code Evidence}'s details.
     */
    private void fillEvidenceCard() {
        nameLabel.setText(evidence.getName());

        String priorityText = String.valueOf(evidence.getPriority());
        if (evidence.getPriority() >= 0 && evidence.getPriority() < priorities.length) {
            priorityText += " - " + priorities[evidence.getPriority()];
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

        return object instanceof EvidenceCard;
    }
}
