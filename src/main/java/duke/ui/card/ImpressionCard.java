package duke.ui.card;

import duke.data.Impression;
import duke.exception.DukeFatalException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * A UI card that displays basic information of an {@code Impression}.
 */
public class ImpressionCard extends UiCard {
    private static final String FXML = "ImpressionCard.fxml";
    private static final String STYLE_NORMAL = "-fx-border-color:black; -fx-border-width: 1; -fx-border-style: solid;";
    private static final String STYLE_PRIMARY = "-fx-border-color:blue; -fx-border-width: 3; -fx-border-style: solid;";

    @FXML
    private Label nameLabel;
    @FXML
    private Label criticalLabel;
    @FXML
    private Label followupLabel;
    @FXML
    private Label descriptionLabel;

    private final Impression impression;
    private boolean isPrimary;

    /**
     * Constructs an ImpressionCard object with the specified {@code Impression}'s details.
     *
     * @param impression Impression object.
     * @param isPrimary  If the Impression object is a primary diagnosis.
     */
    public ImpressionCard(Impression impression, boolean isPrimary) throws DukeFatalException {
        super(FXML);

        this.impression = impression;
        this.isPrimary = isPrimary;
        fillImpressionCard();
    }

    /**
     * Constructs an ImpressionCard object with the specified {@code Impression}'s details.
     *
     * @param impression Impression object.
     */
    public ImpressionCard(Impression impression) throws DukeFatalException {
        super(FXML);

        this.impression = impression;
        this.isPrimary = false;
        fillImpressionCard();
    }

    /**
     * Updates the card primary impression status
     * @param primary if the card is the primary impression
     */
    public void setPrimary(boolean primary) {
        isPrimary = primary;
        fillImpressionCard();
    }

    /**
     * Fills up the UI card with the {@code Impression}'s details.
     */
    private void fillImpressionCard() {
        nameLabel.setText(impression.getName());
        criticalLabel.setText(impression.getCriticalCountStr());
        followupLabel.setText(impression.getFollowUpCountStr());
        descriptionLabel.setText(impression.getDescription());

        if (isPrimary) {
            setStyle(STYLE_PRIMARY);
        } else {
            setStyle(STYLE_NORMAL);
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

        if (!(object instanceof ImpressionCard)) {
            return false;
        }

        ImpressionCard card = (ImpressionCard) object;
        return impression == card.getImpression();
    }

    public Impression getImpression() {
        return impression;
    }
}
